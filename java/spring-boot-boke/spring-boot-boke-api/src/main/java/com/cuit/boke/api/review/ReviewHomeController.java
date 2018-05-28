package com.cuit.boke.api.review;

import com.cuit.boke.api.review.dto.ReviewAddDTO;
import com.cuit.boke.api.review.dto.ReviewQueryDTO;
import com.cuit.boke.api.review.service.ReviewService;
import com.cuit.boke.constant.GwConstants;
import com.cuit.boke.page.PageCommonDTO;
import com.yinjk.web.core.enums.EApiStatus;
import com.yinjk.web.core.exception.BizException;
import com.yinjk.web.core.factory.ResponseFactory;
import com.yinjk.web.core.vo.ResponseVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@Api("评论管理")
@RestController
@RequestMapping("/api/0/review")
public class ReviewHomeController {

    @Autowired
    private ReviewService reviewService;

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ApiOperation("添加评论")
    public ResponseVO status(@RequestBody @Valid ReviewAddDTO reviewAddDTO) throws BizException {
        reviewService.add(reviewAddDTO, null);
        return ResponseFactory.ok(EApiStatus.SUCCESS);
    }

    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ApiOperation("评论列表")
    public ResponseVO list(@RequestBody @Valid ReviewQueryDTO reviewQueryDTO) throws BizException {
        return ResponseFactory.ok(reviewService.list(reviewQueryDTO));
    }



}
