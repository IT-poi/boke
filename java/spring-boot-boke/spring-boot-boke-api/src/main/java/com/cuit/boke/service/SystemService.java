package com.cuit.boke.service;

import com.cuit.boke.config.ServletContextHolder;
import com.cuit.boke.dao.SystemMapper;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.Map;

@Service
public class SystemService {

    private final SystemMapper systemMapper;

    @Autowired
    public SystemService(SystemMapper systemMapper) {
        this.systemMapper = systemMapper;
    }

    /**
     * jdk版本
     */
    public String jdkVersion(){
        return System.getProperty("java.version");
    }

    /**
     * 操作系统版本
     */
    public String osName(){
        return System.getProperty("os.name");
    }

    /**
     * 服务器信息
     * @return
     */
    public String serverInfo(){
        return ServletContextHolder.getServletContext().getServerInfo();
    }

    /**
     * 登录者Ip
     * @param request request
     * @return 登录者Ip
     */
    public String userIp(HttpServletRequest request) {
        return request.getRemoteAddr();
    }

    /**
     * 获取mysql版本信息
     * @return
     */
    public String mysqlVersion(){
        return systemMapper.mysqlVersion();
    }

    /**
     * 系统信息
     * @param request request
     * @return 系统信息
     */
    public Map<String, String> sysInfo(HttpServletRequest request){
        Map<String, String> map = Maps.newHashMap();
        map.put("jdkVersion", jdkVersion());
        map.put("osName", osName());
        map.put("serverInfo", serverInfo());
        map.put("userIp", userIp(request));
        map.put("mysqlVersion", mysqlVersion());
        return map;
    }

    /**
     * 系统时间
     */
    public LocalDateTime sysTime(){
        return LocalDateTime.now();
    }

}
