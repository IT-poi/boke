package com.cuit.boke.api.permission.service;

import com.cuit.boke.beans.entry.SysUser;
import com.cuit.boke.constant.GwConstants;
import com.cuit.boke.dao.LoginRecordMapper;
import com.cuit.boke.dao.SysUserMapper;
import com.cuit.boke.utils.RedisTools;
import com.google.common.collect.Maps;
import com.yinjk.web.core.enums.EApiStatus;
import com.yinjk.web.core.factory.ResponseFactory;
import com.yinjk.web.core.jwt.beans.dto.LoginDTO;
import com.yinjk.web.core.jwt.util.JwtUtil;
import com.yinjk.web.core.util.MD5Util;
import com.yinjk.web.core.vo.ResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class AdminService {

    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private LoginRecordService loginRecordService;

    @Autowired
    private RedisTools redisTools;

    /**
     * 登陆
     * @param loginDTO 用户名 和 密码
     * @return 登陆成功返回token，失败返回失败信息
     */
    public ResponseVO login(LoginDTO loginDTO) {
        System.out.println(MD5Util.getMd5WithSalt("123456"));
        loginDTO.setPassword(MD5Util.getMd5WithSalt(loginDTO.getPassword()));
        SysUser user = sysUserMapper.getBy(loginDTO);
        if (user == null) {
            return ResponseFactory.err("用户名或密码错误！", EApiStatus.ERR_NEED_LOGIN);
        }
        Map<String, Object> map = Maps.newHashMap();
        map.put("userName", user.getUserName());
        map.put("userNickName", user.getUserNickName());
        map.put("userFullName", user.getUserFullName());
        map.put("sex", user.getSex());
        map.put(GwConstants.JWT_USER_ID_FIELD, user.getId());
        String token = JwtUtil.generateToken(map);
        loginRecordService.recordLogin(token);
        return ResponseFactory.ok(token);
    }

    /**
     * 退出登陆，让用户token失效
     * @param token token
     * @return 退出成功
     */
    public ResponseVO logout(String token) {
        redisTools.sAdd(GwConstants.REDIS_USER_TOKEN_LOGOUT_KEY, token);//让token失效
        return ResponseFactory.ok(EApiStatus.SUCCESS);
    }

    /**
     * 获得用户信息
     * @param userName
     * @return
     */
    public SysUser info(String userName) {
       return sysUserMapper.getByUserName(userName);
    }

}
