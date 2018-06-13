package com.cuit.boke.api.article;

import com.cuit.boke.aop.annotation.SysControllerLog;
import com.cuit.boke.article.beans.dto.ArticleDTO;
import com.cuit.boke.article.beans.dto.ArticleQueryDTO;
import com.cuit.boke.article.beans.dto.ArticleUpdateDTO;
import com.cuit.boke.constant.GwConstants;
import com.cuit.boke.constant.UserConstants;
import com.cuit.boke.service.ArticleService;
import com.yinjk.web.core.enums.EApiStatus;
import com.yinjk.web.core.exception.BizException;
import com.yinjk.web.core.factory.ResponseFactory;
import com.yinjk.web.core.vo.ResponseVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/1/manage/article")
@Api("文章管理")
public class ArticleApi {

    @Autowired
    private ArticleService articleService;

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ApiOperation(value = "添加文章", notes = "添加文章")
    @SysControllerLog(description = "添加文章")
    public ResponseVO create(@RequestBody @Valid ArticleDTO articleDTO,
                             @RequestHeader(UserConstants.USER_USERID_FEILD) Integer userId) throws BizException {
        return ResponseFactory.ok(articleService.createArticle(articleDTO , userId));
    }

    @RequestMapping(value = "/sou", method = RequestMethod.POST)
    @ApiOperation(value = "保存或修改文章", notes = "保存或修改文章")
    @SysControllerLog(description = "添加文章")
    public ResponseVO sou(@RequestBody @Valid ArticleDTO articleDTO,
                             @RequestHeader(UserConstants.USER_USERID_FEILD) Integer userId) throws BizException {
        return ResponseFactory.ok(articleService.sou(articleDTO, userId));
    }

    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ApiOperation(value = "文章列表", notes = "文章列表")
    @SysControllerLog(description = "文章列表")
    public ResponseVO list(@RequestBody @Valid ArticleQueryDTO articleDTO,
                             @RequestHeader(UserConstants.USER_USERID_FEILD) Integer userId) throws BizException {
        return ResponseFactory.ok(articleService.list(articleDTO , userId));
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ApiOperation(value = "修改文章", notes = "修改文章")
    @SysControllerLog(description = "修改文章")
    public ResponseVO update(@RequestBody @Valid ArticleUpdateDTO dto,
                             @RequestHeader(UserConstants.USER_USERID_FEILD) Integer userId) throws BizException {
        return ResponseFactory.ok(articleService.update(dto , userId));
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    @ApiOperation(value = "删除文章", notes = "删除文章")
    @SysControllerLog(description = "删除文章")
    public ResponseVO delete(@PathVariable("id") Integer id,
                             @RequestHeader(UserConstants.USER_USERID_FEILD) Integer userId) throws BizException {
        return articleService.delete(id, userId) == 1 ? ResponseFactory.ok(EApiStatus.SUCCESS) : ResponseFactory.err("", EApiStatus.ERR_SYS);
    }

    @RequestMapping(value = "/recycle/{id}", method = RequestMethod.POST)
    @ApiOperation(value = "放入回收站", notes = "放入回收站")
    public ResponseVO recycle(@PathVariable("id") Integer id,
                             @RequestHeader(UserConstants.USER_USERID_FEILD) Integer userId) throws BizException {
        return articleService.recycle(id, userId) == 1 ? ResponseFactory.ok(EApiStatus.SUCCESS) : ResponseFactory.err("", EApiStatus.ERR_SYS);
    }

    @RequestMapping(value = "/recover/{id}", method = RequestMethod.POST)
    @ApiOperation(value = "恢复为草稿", notes = "恢复为草稿")
    public ResponseVO recover(@PathVariable("id") Integer id,
                              @RequestHeader(UserConstants.USER_USERID_FEILD) Integer userId) throws BizException {
        return articleService.recover(id, userId) == 1 ? ResponseFactory.ok(EApiStatus.SUCCESS) : ResponseFactory.err("", EApiStatus.ERR_SYS);
    }

    @RequestMapping(value = "/uploadPic", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    @ApiOperation(value = "上传题图", notes = "上传题图")
    public ResponseVO uploadTemplate(HttpServletRequest request,
                                     @RequestParam(required = false) Integer articleId,
                                     @RequestHeader(UserConstants.USER_USERID_FEILD) Integer userId) throws BizException {
        return ResponseFactory.ok(articleService.uploadTitlePic(request, articleId, userId));
    }

    @RequestMapping(value = "/statistics", method = RequestMethod.POST)
    @ApiOperation(value = "数量统计", notes = "数量统计")
    public ResponseVO statistics(@RequestHeader(UserConstants.USER_USERID_FEILD) Integer userId) throws BizException {
        return ResponseFactory.ok(articleService.statistics(userId));
    }

}
