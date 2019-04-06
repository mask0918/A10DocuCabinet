package com.bst.pidms.service.impl;

import com.bst.pidms.dao.BindRolePermissionMapper;
import com.bst.pidms.entity.BindRolePermission;
import com.bst.pidms.service.BindRolePermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: BST
 * @Date: 2019/4/2 20:14
 */
@Service
public class BindRolePermissionServiceImpl implements BindRolePermissionService {
    @Autowired
    private BindRolePermissionMapper bindRolePermissionMapper;

    @Override
    public List<BindRolePermission> getBinds() {
        return bindRolePermissionMapper.selectAll();
    }

    @Override
    public void addPer(BindRolePermission brp) {
        bindRolePermissionMapper.insert(brp);
    }

    @Override
    public List<Integer> getBindByRoleId(Integer id) {
        return bindRolePermissionMapper.selectPidByRid(id);
    }

    @Override
    public void delBind(Integer id) {
        bindRolePermissionMapper.deleteByPrimaryKey(id);
    }

}
