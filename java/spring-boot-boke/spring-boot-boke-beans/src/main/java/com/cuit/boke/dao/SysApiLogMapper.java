package com.cuit.boke.dao;

import com.cuit.boke.beans.entry.SysApiLog;
import java.util.List;
import java.util.Map;

public interface SysApiLogMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SysApiLog record);

    SysApiLog selectByPrimaryKey(Integer id);

    List<SysApiLog> selectAll();

    int updateByPrimaryKey(SysApiLog record);

    List<SysApiLog> listBy(Map<String, Object> map);
}
