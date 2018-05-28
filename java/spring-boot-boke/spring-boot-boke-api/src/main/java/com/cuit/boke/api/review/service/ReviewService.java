package com.cuit.boke.api.review.service;

import com.cuit.boke.api.review.dto.ReviewAddDTO;
import com.cuit.boke.api.review.dto.ReviewQueryDTO;
import com.cuit.boke.article.dao.ArticleMapper;
import com.cuit.boke.contact.beans.entry.Contact;
import com.cuit.boke.review.beans.entry.Review;
import com.cuit.boke.review.dao.ReviewMapper;
import com.cuit.boke.utils.TransformUtils;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.google.common.collect.Maps;
import com.yinjk.web.core.exception.BizException;
import com.yinjk.web.core.util.BeanMapUtil;
import com.yinjk.web.core.vo.PageVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Map;
import java.util.Objects;

@Service
public class ReviewService {

    @Autowired
    private ReviewMapper reviewMapper;

    @Autowired
    private ArticleMapper articleMapper;

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
        review.setCreateBy(userId);
        review.setCreateAt(new Date());
        reviewMapper.insert(review);
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

