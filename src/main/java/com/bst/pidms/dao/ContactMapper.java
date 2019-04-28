package com.bst.pidms.dao;

import com.bst.pidms.entity.Contact;

import java.util.List;

public interface ContactMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Contact record);

    Contact selectByPrimaryKey(Integer id);

    List<Contact> selectAll();

    List<Contact> selectByUser(Integer id);

    int updateByPrimaryKey(Contact record);
}