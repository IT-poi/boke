package com.cuit.boke.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * describe: 默认的阵列列数
 * creat_user: yinjiankang
 **/

@Configuration
@ConfigurationProperties(prefix = "file")
@Data
public class FileConfig {

    /** 通过ip获取地址url **/
    private String root;

    private String rootPath;

    private String download;
}
