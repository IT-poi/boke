package com.cuit.boke.api.category;

import com.cuit.boke.api.category.service.CategoryService;
import com.cuit.boke.constant.GwConstants;
import com.yinjk.web.core.factory.ResponseFactory;
import com.yinjk.web.core.vo.ResponseVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@Api("文章分类")
@RequestMapping("/api/0/article/category")
public class CategorHomeyApi {

    @Autowired
    private CategoryService categoryService;

    @RequestMapping(value = "{userName}/list/{parentId}", method = {RequestMethod.GET})
    @ApiOperation("获取用户的文章分类列表")
    public ResponseVO list(@PathVariable("userName") String userName,
                           @PathVariable("parentId") Integer parentId){
        return ResponseFactory.ok(categoryService.list(userName, parentId));
    }

    @RequestMapping(value = "/{alias}", method = {RequestMethod.GET})
    @ApiOperation("获取分类信息")
    public ResponseVO detail(@PathVariable("alias") String alias) {
        return ResponseFactory.ok(categoryService.getByAlias(alias));
    }



}
