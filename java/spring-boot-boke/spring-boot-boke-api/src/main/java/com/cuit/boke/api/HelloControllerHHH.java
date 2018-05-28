package com.cuit.boke.api;

import com.cuit.boke.aop.annotation.SysControllerLog;
import com.cuit.boke.beans.entry.SysUser;
import com.cuit.boke.dao.SysUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//@CrossOrigin(origins = "*")
@RestController
public class HelloControllerHHH {

    @Autowired
    private SysUserMapper sysUserMapper;

    @RequestMapping(value = "/get/{id}")
    @SysControllerLog(description = "你好")
    public SysUser get(@PathVariable("id") Integer userId,
                       @RequestHeader("token") String token, HttpServletRequest request, HttpServletResponse response){
        System.out.println("----" + token);
        return sysUserMapper.selectByPrimaryKey(userId);
    }

    @RequestMapping("/getIp")
    public String getIp(HttpServletRequest request){
        return request.getRemoteAddr();
    }

}
