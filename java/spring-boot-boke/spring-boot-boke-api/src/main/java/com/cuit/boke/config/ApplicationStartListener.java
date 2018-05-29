package com.cuit.boke.config;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;


/**
 * spring容器初始化完成会调用onApplicationEvent方法
 */
@Component
public class ApplicationStartListener implements
        ApplicationListener<ContextRefreshedEvent> {
  
    @Override  
    public void onApplicationEvent(ContextRefreshedEvent arg0) {
        //系统初始化工作
    }
}  