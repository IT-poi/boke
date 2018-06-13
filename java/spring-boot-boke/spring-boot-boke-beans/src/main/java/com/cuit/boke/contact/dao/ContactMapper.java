package com.cuit.boke.contact.dao;

import com.cuit.boke.contact.beans.entry.Contact;
import java.util.List;
import java.util.Map;

public interface ContactMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Contact record);

    Contact selectByPrimaryKey(Integer id);

    List<Contact> selectAll();

    int updateByPrimaryKey(Contact record);

    List<Contact> findBy(Map<String, Object> map);

    Integer count();

}