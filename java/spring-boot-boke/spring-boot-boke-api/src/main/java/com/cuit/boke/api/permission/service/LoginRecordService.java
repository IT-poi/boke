package com.cuit.boke.api.permission.service;

import com.cuit.boke.beans.entry.LoginRecord;
import com.cuit.boke.config.RemoteCallConfig;
import com.cuit.boke.constant.GwConstants;
import com.cuit.boke.dao.LoginRecordMapper;
import com.cuit.boke.page.PageCommonDTO;
import com.cuit.boke.utils.JsonMapper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.google.common.collect.Maps;
import com.yinjk.web.core.jwt.util.JwtUtil;
import com.yinjk.web.core.vo.PageVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.cuit.boke.aop.log.HttpLog.getIp;
import static com.cuit.boke.utils.TransformUtils.castTo;

@Service
public class LoginRecordService {

    @Autowired
    private LoginRecordMapper loginRecordMapper;

    @Autowired
    private RemoteCallConfig remoteCallConfig;

    /** logger */
    private static final Logger LOGGER = LoggerFactory.getLogger(LoginRecordService.class);


    /**
     * 查看用户的登陆状态
     *
     * @param userId 用户id
     * @return
     */
    public Map<String, Object> userLoginStatus(Integer userId) {
        return loginRecordMapper.getLoginStatusByUserId(userId);
    }

    /**
     * 分页查询用户的登陆记录
     *
     * @param pageCommonDTO 分页
     * @param userId 用户Id
     * @return 用户的登陆记录
     */
    public PageVO<LoginRecord> list(PageCommonDTO pageCommonDTO, Integer userId) {
        Map<String, Object> paramMap = Maps.newHashMap();
        paramMap.put("loginUserId", userId);
        PageHelper.startPage(pageCommonDTO.getPageNum(), pageCommonDTO.getPageSize());
        PageHelper.orderBy("login_time DESC");
        Page<LoginRecord> pages = (Page<LoginRecord>) loginRecordMapper.listBy(paramMap);
        return new PageVO<>(pages);
    }

    /**
     * 添加用户登陆记录
     *
     * @param token token
     */
    public void recordLogin(String token) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        String ip = getIp(request);
        LoginRecord loginRecord = new LoginRecord();
        loginRecord.setLoginIp(ip);
        loginRecord.setLoginAddress(getIpAddress(ip));
        loginRecord.setLoginTime(new Date());
        Map jwtBody = JwtUtil.getJwtBody(token);
        Integer userId = castTo(jwtBody.get(GwConstants.JWT_USER_ID_FIELD), Integer.class);
        String userName = castTo(jwtBody.get("userName"), String.class);
        loginRecord.setLoginUserId(userId);
        loginRecord.setLoginUserName(userName);
        loginRecordMapper.insert(loginRecord);
    }


    public String getIpAddress(String ip){
        String path = remoteCallConfig.getIpInfo()+ip;
        String line;
        StringBuilder info= new StringBuilder();
        String address = "";
        try {
            URL url = new URL(path);
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            conn.setReadTimeout(10*1000);
            conn.setRequestMethod("GET");
            InputStreamReader inStream = new InputStreamReader(conn.getInputStream(),"UTF-8");
            BufferedReader buffer=new BufferedReader(inStream);
            while((line=buffer.readLine())!=null){
                info.append(line);
            }
            HashMap hashMap = JsonMapper.getInstance().fromJson(info.toString(), HashMap.class);
            if (hashMap.get("code").equals(0)) {
                Map data = (Map) hashMap.get("data");
                address = data.get("country") + "/" + data.get("region") + "/" + data.get("city");
            }

        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
        }
        return address;
    }

}
