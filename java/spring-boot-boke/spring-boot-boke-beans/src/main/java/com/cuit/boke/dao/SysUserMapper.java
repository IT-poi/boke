package com.cuit.boke.dao;

import com.cuit.boke.beans.entry.SysUser;
import com.yinjk.web.core.jwt.beans.dto.LoginDTO;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SysUserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SysUser record);

    SysUser selectByPrimaryKey(Integer id);

    List<SysUser> selectAll();

    int updateByPrimaryKey(SysUser record);

    SysUser getBy(LoginDTO loginDTO);

    SysUser getByUserName(String userName);
}