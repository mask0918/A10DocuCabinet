package com.bst.pidms.service.impl;

import com.bst.pidms.dao.PermissionMapper;
import com.bst.pidms.entity.Permission;
import com.bst.pidms.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: BST
 * @Date: 2019/3/30 20:37
 */
@Service
public class PermissionServiceImpl implements PermissionService {
    @Autowired
    private PermissionMapper mapper;

    @Override
    public List<Permission> getPers() {
        return mapper.selectAll();
    }

    @Override
    public void addPer(Permission permission) {
        mapper.insert(permission);
    }

    @Override
    public Permission getPerByUrl(String url) {
        return mapper.selectByUrl(url);
    }

    @Override
    public Permission getPerByName(String name) {
        return mapper.selectByName(name);
    }

    @Override
    public void delPer(Integer id) {
        mapper.deleteByPrimaryKey(id);
    }

    @Override
    public void modifyPer(Permission permission) {
        mapper.updateByPrimaryKey(permission);
    }

}
