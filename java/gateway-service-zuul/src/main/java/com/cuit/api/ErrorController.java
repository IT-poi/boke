package com.cuit.api;

import com.cuit.redis.RedisTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
public class ErrorController {

    @Autowired
    private RedisTools redisTools;

    @RequestMapping("/error")
    public String error(HttpServletRequest request, HttpServletResponse response){
        return "错误页面 error html : ";
    }
}
