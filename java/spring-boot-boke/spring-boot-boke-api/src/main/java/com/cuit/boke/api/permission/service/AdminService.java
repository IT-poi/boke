package com.cuit.boke.api.permission.service;

import com.cuit.boke.api.admin.dto.ResetDTO;
import com.cuit.boke.beans.entry.SysUser;
import com.cuit.boke.constant.GwConstants;
import com.cuit.boke.dao.LoginRecordMapper;
import com.cuit.boke.dao.SysUserMapper;
import com.cuit.boke.utils.RedisTools;
import com.cuit.boke.utils.TimeUtil;
import com.google.common.collect.Maps;
import com.yinjk.web.core.enums.EApiStatus;
import com.yinjk.web.core.exception.BizException;
import com.yinjk.web.core.factory.ResponseFactory;
import com.yinjk.web.core.jwt.beans.dto.LoginDTO;
import com.yinjk.web.core.jwt.util.JwtUtil;
import com.yinjk.web.core.util.MD5Util;
import com.yinjk.web.core.vo.ResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Objects;

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

    public String reset(ResetDTO resetDTO) throws BizException {
//        LocalDateTime now = LocalDateTime.now();
//        LocalDateTime invalidTime = now.plusMinutes(30);
//        String time = invalidTime.format(TimeUtil.FORMATTER_DEF);
//        redisTools.set(GwConstants.REDIS_RESET_TIME, "time");
//        String url = "http:193.112.112.136/boke-core/api/0/admin?userName=" + userName;
        SysUser sysUser = sysUserMapper.getByUserName(resetDTO.getUserName());
        if (Objects.isNull(sysUser)) {
            throw new BizException("该用户不存在！");
        }
        if (Objects.equals(sysUser.getEmail(), resetDTO.getEmail())) {
            String pass = "123456";
            SysUser sysUser1 = new SysUser();
            sysUser1.setId(sysUser.getId());
            sysUser1.setUserPwd(MD5Util.getMd5WithSalt(pass));
            sysUserMapper.updateByPrimaryKey(sysUser1);
        } else {
            throw new BizException("你输入的邮箱有误！");
        }
        return "密码重置成功！";
    }

}
