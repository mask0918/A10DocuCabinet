package com.bst.pidms.service;

import com.bst.pidms.entity.BindRolePermission;
import com.bst.pidms.entity.Permission;

import java.util.List;

/**
 * @Author: BST
 * @Date: 2019/4/2 19:53
 */
public interface BindRolePermissionService {

    List<BindRolePermission> getBinds();

    void addPer(BindRolePermission brp);

    List<Integer> getBindByRoleId(Integer id);

    void delBind(Integer id);

}
