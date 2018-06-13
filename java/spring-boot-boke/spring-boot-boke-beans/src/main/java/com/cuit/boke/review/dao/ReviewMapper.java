package com.cuit.boke.review.dao;

import com.cuit.boke.review.beans.entry.Review;
import java.util.List;
import java.util.Map;

public interface ReviewMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Review record);

    Review selectByPrimaryKey(Integer id);

    List<Review> selectAll();

    int updateByPrimaryKey(Review record);

    List<Review> findBy(Map<String, Object> map);

    Integer count();

    int deleteByArticle(Integer articleId);

}