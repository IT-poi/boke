package com.cuit.boke.dao;

import com.cuit.boke.beans.entry.LoginRecord;
import java.util.List;
import java.util.Map;

public interface LoginRecordMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(LoginRecord record);

    LoginRecord selectByPrimaryKey(Integer id);

    List<LoginRecord> selectAll();

    List<LoginRecord> listBy(Map<String, Object> map);

    int updateByPrimaryKey(LoginRecord record);

    Map<String, Object> getLoginStatusByUserId(Integer id);


}