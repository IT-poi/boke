package com.cuit.boke.api.permission;

import com.cuit.boke.api.permission.service.LoginRecordService;
import com.cuit.boke.constant.GwConstants;
import com.cuit.boke.page.PageCommonDTO;
import com.yinjk.web.core.exception.BizException;
import com.yinjk.web.core.factory.ResponseFactory;
import com.yinjk.web.core.vo.ResponseVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@Api("登陆记录")
@RestController
@RequestMapping("/api/1/user")
public class LoginRecordController {

    @Autowired
    private LoginRecordService loginRecordService;

    @RequestMapping(value = "/login/status", method = RequestMethod.GET)
    @ApiOperation("用户的登陆记录")
    public ResponseVO status(@RequestHeader(GwConstants.TRANSPARENT_USERID_FIELD) Integer userId) throws BizException {
        return ResponseFactory.ok(loginRecordService.userLoginStatus(userId));
    }

    @RequestMapping(value = "/login/list", method = RequestMethod.POST)
    @ApiOperation("用户历史登陆记录")
    public ResponseVO list(@RequestHeader(GwConstants.TRANSPARENT_USERID_FIELD) Integer userId,
                           @RequestBody @Valid PageCommonDTO pageCommonDTO) throws BizException {
        return ResponseFactory.ok(loginRecordService.list(pageCommonDTO, userId));
    }



}
