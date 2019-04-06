package com.bst.pidms.service;

import com.bst.pidms.entity.Permission;

import java.util.List;

/**
 * @Author: BST
 * @Date: 2019/3/30 20:36
 */
public interface PermissionService {
    List<Permission> getPers();

    void addPer(Permission permission);

    Permission getPerByUrl(String url);

    Permission getPerByName(String name);

    void delPer(Integer id);

    void modifyPer(Permission permission);

}
