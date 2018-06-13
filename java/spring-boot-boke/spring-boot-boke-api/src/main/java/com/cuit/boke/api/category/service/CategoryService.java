package com.cuit.boke.api.category.service;

import com.cuit.boke.api.category.dto.CategoryDTO;
import com.cuit.boke.api.category.dto.CategoryUpdateDTO;
import com.cuit.boke.article.beans.entry.Article;
import com.cuit.boke.article.beans.entry.Category;
import com.cuit.boke.article.dao.ArticleMapper;
import com.cuit.boke.article.dao.CategoryMapper;
import com.google.common.collect.Maps;
import com.yinjk.web.core.exception.BizException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
public class CategoryService {

    /** logger */
    private static final Logger LOGGER = LoggerFactory.getLogger(CategoryService.class);

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private ArticleMapper articleMapper;

    /**
     * 获取用户的分类列表
     * @param userId 用户id
     * @return
     */
    public List<Map<String, Object>> list(Integer userId) {
        Map<String, Object> paramMap = Maps.newHashMap();
        paramMap.put("createBy", userId);
        return categoryMapper.listWithOther(paramMap);
    }

    /**
     * 获取用户的分类列表
     * @param userName 用户账号（登陆名）
     * @return list
     */
    public List<Map<String, Object>> list(String userName, Integer parentId) {
        Map<String, Object> paramMap = Maps.newHashMap();
        paramMap.put("userName", userName);
        paramMap.put("parentId", parentId);
        return categoryMapper.listWithOther(paramMap);
    }

    /**
     * 添加文章分类， 同一个用户的文章分类名和别名不能重复
     *
     * @param categoryDTO
     * @param userId
     */
    public void add(CategoryDTO categoryDTO, Integer userId) throws BizException {
        Map<String, Object> paramMap = Maps.newHashMap();
        paramMap.put("createBy", userId);
        paramMap.put("name", categoryDTO.getName());
        List<Category> categories = categoryMapper.listBy(paramMap);
        if (!CollectionUtils.isEmpty(categories)) {
            throw new BizException("已经有该分类了");
        }
        paramMap.clear();
        paramMap.put("createBy", userId);
        paramMap.put("alias", categoryDTO.getAlias());
        List<Category> categoryList = categoryMapper.listBy(paramMap);
        if (!CollectionUtils.isEmpty(categoryList)) {
            throw new BizException("别名不能重复");
        }
        Category category = new Category();
        BeanUtils.copyProperties(categoryDTO, category);
        category.setCreateBy(userId);
        category.setCreateAt(LocalDateTime.now());
        categoryMapper.insert(category);
    }

    /**
     * 跟新分类信息
     *
     * @param categoryUpdateDTO 要更新的分类信息
     * @param userId 用户id
     * @return 是否更新成功
     * @throws BizException 更新失败原因
     */
    public boolean update(CategoryUpdateDTO categoryUpdateDTO, Integer userId) throws BizException {
        Map<String, Object> paramMap = Maps.newHashMap();
        Category oldCateGory = categoryMapper.selectByPrimaryKey(categoryUpdateDTO.getId());
        if (!Objects.equals(oldCateGory.getName(), categoryUpdateDTO.getName())) {
            paramMap.put("createBy", userId);
            paramMap.put("name", categoryUpdateDTO.getName());
            List<Category> categories = categoryMapper.listBy(paramMap);
            if (!CollectionUtils.isEmpty(categories)) {
                throw new BizException("该分类已存在");
            }
        }
        if (!Objects.equals(oldCateGory.getAlias(), categoryUpdateDTO.getAlias())) {
            paramMap.clear();
            paramMap.put("createBy", userId);
            paramMap.put("alias", categoryUpdateDTO.getAlias());
            List<Category> categoryList = categoryMapper.listBy(paramMap);
            if (!CollectionUtils.isEmpty(categoryList)) {
                throw new BizException("别名不能重复");
            }
        }
        Category category = new Category();
        BeanUtils.copyProperties(categoryUpdateDTO, category);
        category.setUpdateBy(userId);
        category.setUpdateAt(LocalDateTime.now());
        return categoryMapper.updateByPrimaryKey(category) == 1;
    }

    /**
     * 删除分类
     * @param categoryId 分类ID
     * @param userId 操作用户ID
     * @return
     * @throws BizException
     */
    public int delete(Integer categoryId, Integer userId) throws BizException {
        Map<String, Object> paramMap = Maps.newHashMap();
        paramMap.put("categoryId", categoryId);
        List<Article> articles = articleMapper.listBy(paramMap);
        if (!CollectionUtils.isEmpty(articles)) { //分类下有文章不能删除
            LOGGER.error("分类下有文章，不能删除！");
            throw new BizException("该分类下存在文章，不能删除该分类！");
        }
        return categoryMapper.deleteByPrimaryKey(categoryId);
    }

    /**
     * 根据分类id获取分类信息
     *
     * @param categoryId 分类id
     * @return 分类信息
     */
    public Category getById(Integer categoryId){
        return categoryMapper.selectByPrimaryKey(categoryId);
    }

    /**
     * 根据分类id获取分类信息
     *
     * @param alias 分类别名
     * @return 分类信息
     */
    public Category getByAlias(String alias){
        return categoryMapper.getByAlias(alias);
    }


}
