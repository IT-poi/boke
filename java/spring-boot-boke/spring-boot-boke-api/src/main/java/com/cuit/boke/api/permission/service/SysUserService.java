package com.cuit.boke.api.permission.service;

import com.cuit.boke.api.permission.dto.UserUpdateDTO;
import com.cuit.boke.beans.entry.SysUser;
import com.cuit.boke.constant.GwConstants;
import com.cuit.boke.dao.LoginRecordMapper;
import com.cuit.boke.dao.SysUserMapper;
import com.cuit.boke.utils.RedisTools;
import com.google.common.collect.Maps;
import com.yinjk.web.core.enums.EApiStatus;
import com.yinjk.web.core.exception.BizException;
import com.yinjk.web.core.factory.ResponseFactory;
import com.yinjk.web.core.jwt.beans.dto.LoginDTO;
import com.yinjk.web.core.jwt.util.JwtUtil;
import com.yinjk.web.core.util.MD5Util;
import com.yinjk.web.core.vo.ResponseVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Objects;

@Service
public class SysUserService {

    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private RedisTools redisTools;


    public SysUser getUserInfo(Integer userId){
        return sysUserMapper.selectByPrimaryKey(userId);
    }

    public SysUser getOne(){
        return sysUserMapper.getOne();
    }

    public void update(Integer userId, UserUpdateDTO userUpdateDTO) throws BizException {
        SysUser sysUser = sysUserMapper.selectByPrimaryKey(userId);
        BeanUtils.copyProperties(userUpdateDTO, sysUser);
        if (StringUtils.isNotBlank(userUpdateDTO.getUserPwdNew()) || StringUtils.isNotBlank(userUpdateDTO.getUserPwdOld())) {
            //修改密码
            if (!Objects.equals(MD5Util.getMd5WithSalt(userUpdateDTO.getUserPwdOld()), sysUser.getUserPwd())) {
                throw new BizException("原密码错误！");
            }
            if (Objects.equals(userUpdateDTO.getUserPwdNew(), userUpdateDTO.getUserPwdOld())) {
                throw new BizException("密码未作出修改！");
            }
            if (StringUtils.isBlank(userUpdateDTO.getUserPwdNew()) || userUpdateDTO.getUserPwdNew().length() < 6) {
                throw new BizException("新密码必须超过6位！");
            }
            sysUser.setUserPwd(MD5Util.getMd5WithSalt(userUpdateDTO.getUserPwdNew()));
        }
        sysUserMapper.updateByPrimaryKey(sysUser);
    }

}
