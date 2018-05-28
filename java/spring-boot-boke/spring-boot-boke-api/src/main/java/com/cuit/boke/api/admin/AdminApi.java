package com.cuit.boke.api.admin;

import com.cuit.boke.aop.annotation.SysControllerLog;
import com.cuit.boke.aop.annotation.SysLoginLog;
import com.cuit.boke.api.permission.service.AdminService;
import com.cuit.boke.api.permission.service.SysUserService;
import com.cuit.boke.constant.GwConstants;
import com.yinjk.web.core.exception.BizException;
import com.yinjk.web.core.factory.ResponseFactory;
import com.yinjk.web.core.jwt.beans.dto.LoginDTO;
import com.yinjk.web.core.vo.ResponseVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Api("权限模块")
@RequestMapping("/api/0/admin")
@RestController
public class AdminApi {

    @Autowired
    private AdminService adminService;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ApiOperation("登陆")
    @SysControllerLog(description = "登陆")
    @SysLoginLog
    public ResponseVO login(@RequestBody @Valid LoginDTO loginDTO) throws BizException {
        return adminService.login(loginDTO);
    }


    @RequestMapping(value = "/register", method = RequestMethod.GET)
    @ApiOperation("注册")
    @SysControllerLog(description = "注册")
    public ResponseVO register() throws BizException {
        return ResponseFactory.ok("hello");
    }

    @RequestMapping(value = "/{userName}/info", method = RequestMethod.GET)
    @ApiOperation("用户信息")
    public ResponseVO info(@PathVariable("userName") String userName) {
        return ResponseFactory.ok(adminService.info(userName));
    }

}
