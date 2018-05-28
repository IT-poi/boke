package com.cuit.boke.api.article;

import com.cuit.boke.aop.annotation.SysControllerLog;
import com.cuit.boke.article.beans.dto.ArticleQueryDTO;
import com.cuit.boke.article.beans.dto.EsArticleQueryDTO;
import com.cuit.boke.page.PageCommonDTO;
import com.cuit.boke.service.ArticleService;
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
@Api("前端文章展示")
@RequestMapping("/api/0/home/article")
public class ArticleHomeApi {

    @Autowired
    private ArticleService articleService;

    @RequestMapping(value = "/{articleId}", method = {RequestMethod.POST, RequestMethod.GET})
    @ApiOperation("通过id拿到文章信息")
    public ResponseVO get(@PathVariable("articleId") Integer articleId){
        return ResponseFactory.ok(articleService.getById(articleId));
    }

    @RequestMapping(value = "/{articleId}/praise", method = {RequestMethod.POST, RequestMethod.GET})
    @ApiOperation("给文章点赞")
    public ResponseVO praise(@PathVariable("articleId") Integer articleId){
        return articleService.praise(articleId) == 1 ? ResponseFactory.ok(EApiStatus.SUCCESS) : ResponseFactory.err("error", EApiStatus.ERR_SYS);
    }

    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ApiOperation(value = "文章列表", notes = "文章列表")
    @SysControllerLog(description = "文章列表")
    public ResponseVO list(@RequestBody @Valid ArticleQueryDTO articleDTO ) throws BizException {
        return ResponseFactory.ok(articleService.list(articleDTO, null));
    }

    @RequestMapping(value = "/elasticsearch", method = RequestMethod.POST)
    @ApiOperation(value = "全文检索文章列表", notes = "文章列表")
    @SysControllerLog(description = "文章列表")
    public ResponseVO elasticsearch(@RequestBody @Valid EsArticleQueryDTO esArticleQueryDTO) throws BizException {
        return ResponseFactory.ok(articleService.elasticsearch(esArticleQueryDTO, false));
    }

}
