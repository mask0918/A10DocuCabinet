package com.bst.pidms.service;

import com.bst.pidms.entity.Role;

import java.util.List;

/**
 * @Author: BST
 * @Date: 2019/4/2 10:26
 */
public interface RoleService {
    List<Role> getRoles();

    void addRole(Role role);

    Role getRoleById(Integer id);

    Role getRoleByName(String name);

    void delRole(Integer id);

    void modifyRole(Role role);
}
