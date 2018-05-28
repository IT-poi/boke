package com.cuit.boot.start;

import com.cuit.filter.AccessFilter;
import com.cuit.filter.ErrorFilter;
import com.cuit.filter.GwCorsFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@SpringBootApplication
@EnableZuulProxy
@EnableEurekaClient
@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class})
@EnableCircuitBreaker
@RestController
public class ApplicationStarter {

    public static void main(String[] args){
        SpringApplication.run(ApplicationStarter.class, args);
    }

    @Bean
    public AccessFilter accessFilter(){
        return new AccessFilter();
    }

//    @Bean
//    public ErrorFilter errorFilter(){
//        return new ErrorFilter();
//    }

    @Bean
    public GwCorsFilter gwCorsFilter(){
        return new GwCorsFilter();
    }
    @RequestMapping("/hello")
    public String hello() {
        return "hello world";
    }
}
