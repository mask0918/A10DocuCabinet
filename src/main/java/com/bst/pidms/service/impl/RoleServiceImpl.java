package com.bst.pidms.service.impl;

import com.bst.pidms.dao.RoleMapper;
import com.bst.pidms.entity.Role;
import com.bst.pidms.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: BST
 * @Date: 2019/4/2 10:28
 */
@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleMapper roleMapper;

    @Override
    public List<Role> getRoles() {
        return roleMapper.selectAll();
    }

    @Override
    public void addRole(Role role) {
        roleMapper.insert(role);
    }

    @Override
    public Role getRoleById(Integer id) {
        return roleMapper.selectByPrimaryKey(id);
    }

    @Override
    public Role getRoleByName(String name) {
        return roleMapper.selectByName(name);
    }

    @Override
    public void delRole(Integer id) {
        roleMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void modifyRole(Role role) {
        roleMapper.updateByPrimaryKey(role);
    }

}
