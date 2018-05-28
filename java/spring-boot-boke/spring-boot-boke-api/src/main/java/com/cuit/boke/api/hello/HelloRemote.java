package com.cuit.boke.api.hello;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

//远程调用 name：服务提供者name, 即spring.application.name配置的名称
//       fallback: 熔断回调!
@FeignClient(name = "spring-cloud-producer", fallback = HelloRemoteHystrix.class)
public interface HelloRemote {

    @RequestMapping("/hello")
    public String hello(@RequestParam(value = "name") String name);
}
