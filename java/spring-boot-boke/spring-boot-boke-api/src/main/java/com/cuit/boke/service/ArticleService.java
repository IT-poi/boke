package com.cuit.boke.service;

import com.cuit.boke.api.files.service.FileService;
import com.cuit.boke.article.beans.dto.ArticleDTO;
import com.cuit.boke.article.beans.dto.ArticleQueryDTO;
import com.cuit.boke.article.beans.dto.ArticleUpdateDTO;
import com.cuit.boke.article.beans.dto.EsArticleQueryDTO;
import com.cuit.boke.article.beans.entry.Article;
import com.cuit.boke.article.beans.entry.ArticleLabel;
import com.cuit.boke.article.beans.entry.Category;
import com.cuit.boke.article.dao.ArticleLabelMapper;
import com.cuit.boke.article.dao.CategoryMapper;
import com.cuit.boke.es.repository.EsArticleRepository;
import com.cuit.boke.es.service.EsArticleService;
import com.cuit.boke.label.beans.entry.Label;
import com.cuit.boke.beans.entry.SysUser;
import com.cuit.boke.article.dao.ArticleMapper;
import com.cuit.boke.label.dao.LabelMapper;
import com.cuit.boke.dao.SysUserMapper;
import com.cuit.boke.enums.article.ArticleStatusEnum;
import com.cuit.boke.utils.TransformUtils;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.google.common.collect.Maps;
import com.yinjk.web.core.enums.EApiStatus;
import com.yinjk.web.core.exception.BizException;
import com.yinjk.web.core.vo.PageVO;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.cuit.boke.utils.TransformUtils.castTo;

@Service
public class ArticleService {

    @Autowired
    private ArticleMapper articleMapper;

    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private LabelMapper labelMapper;

    @Autowired
    private ArticleLabelMapper articleLabelMapper;

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private EsArticleService esArticleService;

    @Autowired
    private FileService fileService;


    /** logger */
    private static final Logger LOGGER = LoggerFactory.getLogger(ArticleService.class);

    /**
     * 添加文章
     * @param articleDTO articleDTO
     * @param userId 用户Id
     * @return 是否添加成功
     */
    @Transactional(rollbackFor = Exception.class)
    public Article createArticle(ArticleDTO articleDTO, Integer userId) throws BizException {
        Article article = new Article();
        BeanUtils.copyProperties(articleDTO, article);
        if (StringUtils.isBlank(article.getAuthor())) {
            SysUser sysUser = sysUserMapper.selectByPrimaryKey(userId);
            article.setAuthor(sysUser.getUserNickName());
        }
        article.setCreateAt(new Date());
        article.setCreateBy(userId);
        int i = articleMapper.insert(article);
        if (i <= 0) {
            String msg = "创建失败";
            LOGGER.error(msg);
            throw new BizException(msg);
        }
        Category category = categoryMapper.selectByPrimaryKey(article.getCategoryId());
        article.setCategoryName(category.getName());
        esArticleService.saveArticle(article);
        return article;
    }

    /**
     * 创建或保存文章
     * @param articleDTO 要保存的文章
     * @param userId 用户Id
     * @throws BizException 异常
     */
    @Transactional(rollbackFor = Exception.class)
    public Article sou(ArticleDTO articleDTO, Integer userId) throws BizException {
        //只要不是已发布，只需要有内容就可以保存
        if (Objects.equals(articleDTO.getStatus(), ArticleStatusEnum.PUBLISHED.getKey())) {
            if (articleDTO.getType() == null || StringUtils.isBlank(articleDTO.getLabelNames()) || articleDTO.getCategoryId() == null) {
                String msg = "请完善信息再发布！";
                LOGGER.error(msg);
                throw new BizException(msg);
            }
        }
        if (StringUtils.isBlank(articleDTO.getHtmlContent())
                || StringUtils.isBlank(articleDTO.getStringContent())
                || StringUtils.isBlank(articleDTO.getTitle())) {
            String msg = "文章内容不能为空！";
            LOGGER.error(msg);
            throw new BizException(msg);
        }
        if (null == articleDTO.getId()) { //新增
            Article article = createArticle(articleDTO, userId);
            handleLabels(article.getId(), article.getLabelNames(), userId);
            return article;
        } else { //修改
            ArticleUpdateDTO articleUpdateDTO = new ArticleUpdateDTO();
            BeanUtils.copyProperties(articleDTO, articleUpdateDTO);
            Article article = update(articleUpdateDTO, userId);
            handleLabels(article.getId(), article.getLabelNames(), userId);
            return article;
        }
    }

    /**
     * 处理文章标签逻辑
     * @param articleId
     * @param labels
     * @param userId
     */
    @Transactional(rollbackFor = Exception.class)
    public void handleLabels(Integer articleId, String labels, Integer userId){
        Map<String, Object> paramMap = Maps.newHashMap();
        paramMap.put("articleId", articleId);
        //该文章原来的标签
        List<Map<String, Object>> articleLabels = articleLabelMapper.listByWithLabel(paramMap);
        //修改之后的标签
        List<String> labelNames = TransformUtils.putIdsToList(labels, String.class);
        //得到交集
        List<String> intersection = articleLabels.stream()
                .filter(articleLabel -> labelNames.contains(articleLabel.get("labelName")))
                .map(articleLabel -> castTo(articleLabel.get("labelName"), String.class))
                .collect(Collectors.toList());

        //原来的标签去掉交集剩下的表示要删除的标签
        articleLabels.removeIf(articleLabel -> intersection.contains(articleLabel.get("labelName")));
        //修改之后的标签去掉交集之后表示要添加的标签
        labelNames.removeIf(intersection::contains);

        //先删除需要删除的标签
        for (Map<String, Object> articleLabel : articleLabels) {
            Integer labelId = castTo(articleLabel.get("labelId"), Integer.class);
            Integer articleLabelId = castTo(articleLabel.get("id"), Integer.class);
            //更新标签下的文章数量减一
            labelMapper.minusArticle(labelId);
            articleLabelMapper.deleteByPrimaryKey(articleLabelId);
        }
        //添加需要添加的标签
        for (String labelName : labelNames) {
            Label label = labelMapper.getByName(labelName);
            if (label == null) { //该标签不存在，创建该标签
                label = new Label();
                label.setName(labelName);
                label.setArticleCount(0);
                label.setCreateAt(new Date());
                label.setCreateBy(userId);
                labelMapper.insert(label);
            }
            ArticleLabel articleLabel = new ArticleLabel();
            articleLabel.setArticleId(articleId);
            articleLabel.setLabelId(label.getId());
            articleLabel.setCreateAt(new Date());
            articleLabel.setCreateBy(userId);
            articleLabelMapper.insert(articleLabel);
            //更新标签下的文章数量加一
            labelMapper.plusArticle(label.getId());
        }
    }

    /**
     * 分页查询文章列表
     *
     * @param dto 查询参数
     * @param userId 用户id
     * @return 查询结果
     * @throws BizException 系统异常：反射错误
     */
    public PageVO<Map<String, Object>> list(ArticleQueryDTO dto, Integer userId) throws BizException {
        dto.setCreateBy(userId);
        if (userId == null) { //前端只返回已发布的文章
            dto.setStatus(ArticleStatusEnum.PUBLISHED.getKey());
        }
        PageHelper.startPage(dto.getPageNum(), dto.getPageSize());
        if (StringUtils.isNotBlank(dto.getSorter())) {
            PageHelper.orderBy(dto.getSorter());
        } else {
            PageHelper.orderBy("a.create_at DESC");
        }
        Map<String, ? super Object> map = TransformUtils.putObjToMap(dto);
        Page<Map<String, Object>> page = (Page<Map<String, Object>>)articleMapper.listWithOther(map);
        return new PageVO<>(page);
    }

    /**
     * 修改文章内容
     *
     * @param dto 修改dto
     * @param userId 用户id
     * @return 修改影响的行数
     */
    public Article update(ArticleUpdateDTO dto, Integer userId) throws BizException {
        Article article = new Article();
        BeanUtils.copyProperties(dto, article);
        article.setUpdateAt(new Date());
        article.setUpdateBy(userId);
        int i = articleMapper.updateByPrimaryKey(article);
        if (i <= 0) {
            String msg = "保存失败";
            LOGGER.error(msg);
            throw new BizException(msg);
        }
        return article;
    }

    /**
     * 删除文章
     *
     * @param id id
     * @param userId 用户id
     * @return
     */
    public int delete(Integer id, Integer userId) throws BizException {
        Article article = getById(id);
        if (Objects.equals(article.getCreateBy(), userId)) { //有权限
            return articleMapper.deleteByPrimaryKey(id);
        } else {
            String msg = EApiStatus.ERR_PERMISSION.getMessage();
            LOGGER.error(msg);
            throw new BizException(msg);
        }
    }


    /**
     * 文章点赞
     *
     * @param articleId 文章id
     */
    public int praise(Integer articleId){
        return articleMapper.plusPraise(articleId);
    }

    /**
     * 通过文章id查询文章详情
     *
     * @param articleId 文章id
     * @return 文章详情
     */
    public Article getById(Integer articleId){
        articleMapper.plusReadCount(articleId);
        return articleMapper.selectByPrimaryKey(articleId);
    }

    /**
     * 按分值权重分页查询文章列表
     * @param esArticleQueryDTO 分页查询条件
     * @param all 是否查询所有，博客前端只能查询已发布，后端可以查询所有
     * @return
     */
    public PageVO<Article> elasticsearch(EsArticleQueryDTO esArticleQueryDTO, boolean all) {
        return esArticleService.searchArticle(esArticleQueryDTO.getPageNum() - 1, esArticleQueryDTO.getPageSize(), esArticleQueryDTO.getKeywords(), all);
    }

    /**
     * 上传题图
     * @param request
     * @param articleId
     * @param userId
     * @return
     * @throws BizException
     */
    public String uploadTitlePic(HttpServletRequest request, Integer articleId, Integer userId) throws BizException {
        String url = fileService.uploadPic(request);
        if (!Objects.isNull(articleId)) {
            //如果文章id不为空，保存到文章题图中
            Article article = new Article();
            article.setUpdateAt(new Date());
            article.setUpdateBy(userId);
            article.setPicUrl(url);
            articleMapper.updateByPrimaryKey(article);
        }
        return url;
    }
}
