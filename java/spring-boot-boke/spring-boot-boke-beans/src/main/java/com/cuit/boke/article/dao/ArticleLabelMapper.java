package com.cuit.boke.article.dao;

import com.cuit.boke.article.beans.entry.ArticleLabel;
import java.util.List;
import java.util.Map;

public interface ArticleLabelMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ArticleLabel record);

    ArticleLabel selectByPrimaryKey(Integer id);

    List<ArticleLabel> selectAll();

    int updateByPrimaryKey(ArticleLabel record);

    List<ArticleLabel> listBy(Map<String, Object> map);

    List<Map<String, Object>> listByWithLabel(Map<String, Object> map);



}