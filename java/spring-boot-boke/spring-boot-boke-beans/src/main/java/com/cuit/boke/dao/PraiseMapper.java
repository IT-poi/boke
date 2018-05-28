package com.cuit.boke.dao;

import com.cuit.boke.beans.entry.Praise;
import java.util.List;

public interface PraiseMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Praise record);

    Praise selectByPrimaryKey(Integer id);

    List<Praise> selectAll();

    int updateByPrimaryKey(Praise record);
}