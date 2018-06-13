package com.cuit.boke.start;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

@SpringBootApplication
@ComponentScans(value = {@ComponentScan("com.cuit"), @ComponentScan("com.yinjk")})
@MapperScan({"com.cuit.boke.*.dao", "com.cuit.boke.dao"})
@EnableElasticsearchRepositories(basePackages = "com.cuit.boke.es") //es扫描 repository的位置
@EnableFeignClients
@ServletComponentScan("com.cuit")
@EnableDiscoveryClient
@EnableCircuitBreaker
@EnableHystrixDashboard
public class SpringBootApplicationStarter {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootApplicationStarter.class, args);
	}
}
