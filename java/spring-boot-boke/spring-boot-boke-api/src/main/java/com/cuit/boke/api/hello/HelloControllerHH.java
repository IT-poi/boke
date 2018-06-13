package com.cuit.boke.api.hello;

import com.yinjk.web.core.util.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloControllerHH {
    @Autowired
    private HelloRemote helloRemote;

    @RequestMapping("/hello/{name}")
    public String hello(@PathVariable("name") String name){
        String md5WithSalt = MD5Util.getMd5WithSalt("123456");
        System.out.println(md5WithSalt);
        return helloRemote.hello(name);
    }

}
