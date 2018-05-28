package com.cuit.boke.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * describe: 默认的阵列列数
 * creat_user: yinjiankang
 **/

@Configuration
@ConfigurationProperties(prefix = "url")
public class RemoteCallConfig {

    /** 通过ip获取地址url **/
    private String ipInfo;

    public String getIpInfo() {
        return ipInfo;
    }

    public void setIpInfo(String ipInfo) {
        this.ipInfo = ipInfo;
    }
}
