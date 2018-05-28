package com.cuit.boke.article.dao;

import com.cuit.boke.article.beans.entry.ArticleCategory;
import java.util.List;
import java.util.Map;

public interface ArticleCategoryMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ArticleCategory record);

    ArticleCategory selectByPrimaryKey(Integer id);

    List<ArticleCategory> selectAll();

    int updateByPrimaryKey(ArticleCategory record);

}