package com.cuit.boke.dao;

import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Repository
public interface SystemMapper {

    @Select("select version() from dual")
    String mysqlVersion();
}
