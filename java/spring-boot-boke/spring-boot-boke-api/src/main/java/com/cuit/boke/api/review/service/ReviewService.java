package com.cuit.boke.api.review.service;

import com.cuit.boke.api.permission.service.SysUserService;
import com.cuit.boke.api.review.dto.ReviewAddDTO;
import com.cuit.boke.api.review.dto.ReviewQueryDTO;
import com.cuit.boke.article.beans.entry.Article;
import com.cuit.boke.article.dao.ArticleMapper;
import com.cuit.boke.beans.entry.SysUser;
import com.cuit.boke.contact.beans.entry.Contact;
import com.cuit.boke.review.beans.entry.Review;
import com.cuit.boke.review.dao.ReviewMapper;
import com.cuit.boke.utils.MailService;
import com.cuit.boke.utils.SensitiveWordFilter;
import com.cuit.boke.utils.TransformUtils;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.google.common.collect.Maps;
import com.yinjk.web.core.exception.BizException;
import com.yinjk.web.core.util.BeanMapUtil;
import com.yinjk.web.core.vo.PageVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Map;
import java.util.Objects;

@Service
public class ReviewService {

    /** logger */
    private static final Logger LOGGER = LoggerFactory.getLogger(ReviewService.class);

    @Autowired
    private ReviewMapper reviewMapper;

    @Autowired
    private ArticleMapper articleMapper;

    @Autowired
    private MailService mailService;

    @Autowired
    private SensitiveWordFilter sensitiveWordFilter;

    @Autowired
    private SysUserService sysUserService;

    /**
     * 添加评论
     *
     * @param reviewAddDTO 评论dto
     * @param userId 用户id
     */
    @Transactional(rollbackFor = Exception.class)
    public void add(ReviewAddDTO reviewAddDTO, Integer userId) {
        Review review = new Review();
        BeanUtils.copyProperties(reviewAddDTO, review);
        String content = review.getContent();
        //过滤敏感词
        content = sensitiveWordFilter.replaceSensitiveWord(content, SensitiveWordFilter.maxMatchType, "*");
        review.setContent(content);
        review.setCreateBy(userId);
        review.setCreateAt(new Date());
        reviewMapper.insert(review);
        //发送邮件通知
        Integer parentId = review.getParentId();
        Integer articleId = review.getArticleId();
        Article article = articleMapper.selectByPrimaryKey(articleId);
        if (!Objects.isNull(parentId)) { //通知评论的人
            Review parentReview = reviewMapper.selectByPrimaryKey(parentId);
            String mailContext = "您好：" + parentReview.getUserName() + "！<br>" +
                    "您在：<a href=\"http://193.112.112.136/\">Fool的个人博客</a>网站中对文章" +
                    "<a href=\"http://193.112.112.136/#/article/" + articleId + "\">" + article.getTitle() + "</a>的评论：" +
                    parentReview.getContent() + "<label style='color:green'>有了回复！</label><br>" +
                    "回复内容是：<br>" + review.getContent();
            try {
                mailService.sendMail(parentReview.getEmail(), "您在Fool个人博客的评论有了新回复", mailContext);
            } catch (BizException e) {
                LOGGER.error(e.getMsg(), e);
            }
        }
        //通知博主
        SysUser userInfo = sysUserService.getUserInfo(1);
        if (Objects.isNull(userInfo)) {
            userInfo = sysUserService.getOne();
        }
        try {
            String mailContext = "你好：" + userInfo.getUserNickName() + "！<br>" +
                "您的文章：<a href=\"http://193.112.112.136/#/article/"+articleId+"\">" + article.getTitle() + "</a>下面有了新评论。<br>" +
                "评论内容是：<br>" + review.getContent();
            mailService.sendMail(userInfo.getEmail(), "Fool个人博客有人评论了你", mailContext);
        } catch (BizException e) {
            LOGGER.error(e.getMsg(), e);
        }
        articleMapper.plusReviewCount(reviewAddDTO.getArticleId());
    }

    /**
     * 分页查询评论列表
     *
     * @param reviewQueryDTO 查询条件
     * @return 查询结果
     */
    public PageVO<Contact> list(ReviewQueryDTO reviewQueryDTO){
        PageHelper.startPage(reviewQueryDTO.getPageNum(), reviewQueryDTO.getPageSize());
        PageHelper.orderBy("create_at desc");
        Map<String, Object> paramMap = BeanMapUtil.beanToMap(reviewQueryDTO);
        Page<Review> page = (Page<Review>) reviewMapper.findBy(paramMap);
        return new PageVO<>(page);
    }


    /**
     * 删除评论
     *
     * @param reviewId 评论id
     * @param userId 用户id
     */
    @Transactional(rollbackFor = Exception.class)
    public void delete(Integer reviewId, Integer userId) throws BizException {
        Review review = reviewMapper.selectByPrimaryKey(reviewId);
        if (Objects.isNull(review)) {
            throw new BizException("该评论不存在！");
        }
        articleMapper.minusReviewCount(review.getArticleId()); //文章评论减一
        reviewMapper.deleteByPrimaryKey(reviewId);
    }
}

