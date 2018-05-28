package com.cuit.boke.api.permission;

import com.cuit.boke.aop.annotation.SysControllerLog;
import com.cuit.boke.api.permission.dto.UserUpdateDTO;
import com.cuit.boke.api.permission.service.AdminService;
import com.cuit.boke.api.permission.service.SysUserService;
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

@Api("权限模块")
@RequestMapping("/api/1/permission/admin")
@RestController
public class AdminController {

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private AdminService adminService;

    @RequestMapping(value = "/info", method = RequestMethod.GET)
    @ApiOperation("用户信息")
    public ResponseVO info(@RequestHeader(GwConstants.TRANSPARENT_USERID_FIELD) Integer userId) throws BizException {
        return ResponseFactory.ok(sysUserService.getUserInfo(userId));
    }

    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    @ApiOperation("退出登陆")
    public ResponseVO logout(@RequestHeader(GwConstants.TRANSPARENT_TOKEN_FIELD) String token) throws BizException {
        return adminService.logout(token);
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ApiOperation("修改用户信息")
    public ResponseVO update(@RequestHeader(GwConstants.TRANSPARENT_USERID_FIELD) Integer userId,
                             @RequestBody @Valid UserUpdateDTO userUpdateDTO) throws BizException {
        sysUserService.update(userId, userUpdateDTO);
        return ResponseFactory.ok(EApiStatus.SUCCESS);
    }

}
