package com.cuit.boke.article.dao;

import com.cuit.boke.article.beans.entry.Category;
import java.util.List;
import java.util.Map;

public interface CategoryMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Category record);

    Category selectByPrimaryKey(Integer id);

    Category getByAlias(String alias);

    List<Category> selectAll();

    int updateByPrimaryKey(Category record);

    List<Category> listBy(Map<String, Object> map);

    List<Map<String, Object>> listWithOther(Map<String, Object> map);
}