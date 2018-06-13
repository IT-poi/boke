package com.cuit.boke.article.dao;

import com.cuit.boke.article.beans.entry.Article;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface ArticleMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Article record);

    Article selectByPrimaryKey(Integer id);

    List<Article> selectAll();

    int updateByPrimaryKey(Article record);

    /**
     * 文章数量
     *
     * @return 数量
     */
    Integer count();

    /**
     * 文章阅读量
     *
     * @return
     */
    Integer readCount();
    /**
     * 根据条件查询文章列表
     *
     * @param map 查询条件
     * @return 查询结果
     */
    List<Article> listBy(Map<String, Object> map);

    /**
     * 根据条件查询文章列表,联合分类等信息
     *
     * @param map 查询条件
     * @return 查询结果
     */
    List<Map<String, Object>> listWithOther(Map<String, Object> map);

    /**
     * 文章点赞数+1
     *
     * @param articleId 文章id
     * @return 影响行数
     */
    int plusPraise(int articleId);

    /**
     * 文章阅读量+1
     *
     * @param articleId 文章id
     * @return 影响行数
     */
    int plusReadCount(int articleId);

    /**
     * 文章评论量+1
     *
     * @param articleId 文章id
     * @return 影响行数
     */
    int plusReviewCount(int articleId);

    /**
     * 文章评论量-1
     *
     * @param articleId 文章id
     * @return 影响行数
     */
    int minusReviewCount(int articleId);

}