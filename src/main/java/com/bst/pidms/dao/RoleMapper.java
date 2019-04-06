package com.bst.pidms.dao;

import com.bst.pidms.entity.Role;

import java.util.List;

public interface RoleMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Role record);

    Role selectByPrimaryKey(Integer id);

    Role selectByName(String name);

    List<Role> selectAll();

    int updateByPrimaryKey(Role record);
}