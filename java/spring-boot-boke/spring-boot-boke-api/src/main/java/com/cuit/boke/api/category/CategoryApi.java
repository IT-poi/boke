package com.cuit.boke.api.category;

import com.cuit.boke.api.category.dto.CategoryDTO;
import com.cuit.boke.api.category.dto.CategoryUpdateDTO;
import com.cuit.boke.api.category.service.CategoryService;
import com.cuit.boke.constant.GwConstants;
import com.yinjk.web.core.enums.EApiStatus;
import com.yinjk.web.core.exception.BizException;
import com.yinjk.web.core.factory.ResponseFactory;
import com.yinjk.web.core.vo.ResponseVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@Api("文章分类")
@RequestMapping("/api/1/article/category")
public class CategoryApi {

    @Autowired
    private CategoryService categoryService;

    @RequestMapping(value = "add", method = {RequestMethod.POST})
    @ApiOperation("获取用户的文章分类列表")
    public ResponseVO add(@RequestHeader(GwConstants.TRANSPARENT_USERID_FIELD) Integer userId,
                          @RequestBody @Valid CategoryDTO categoryDTO) throws BizException {
        categoryService.add(categoryDTO, userId);
        return ResponseFactory.ok(EApiStatus.SUCCESS);
    }

    @RequestMapping(value = "update", method = {RequestMethod.POST})
    @ApiOperation("获取用户的文章分类列表")
    public ResponseVO update(@RequestHeader(GwConstants.TRANSPARENT_USERID_FIELD) Integer userId,
                          @RequestBody @Valid CategoryUpdateDTO categoryUpdateDTO) throws BizException {
        return categoryService.update(categoryUpdateDTO, userId) ? ResponseFactory.ok(EApiStatus.SUCCESS) : ResponseFactory.err("更新失败", EApiStatus.ERR_SYS);
    }

    @RequestMapping(value = "/list", method = {RequestMethod.POST})
    @ApiOperation("获取用户的文章分类列表")
    public ResponseVO get(@RequestHeader(GwConstants.TRANSPARENT_USERID_FIELD) Integer userId){
        return ResponseFactory.ok(categoryService.list(userId));
    }


}
