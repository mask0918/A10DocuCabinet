package com.bst.pidms.dao;

import com.bst.pidms.entity.BindRolePermission;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BindRolePermissionMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(BindRolePermission record);

    BindRolePermission selectByPrimaryKey(Integer id);

    BindRolePermission selectByPidAndRid(@Param("roleId") Integer roleId, @Param("permissionId") Integer permissionId);

    List<BindRolePermission> selectAll();

    List<Integer> selectPidByRid(Integer roleId);

    int updateByPrimaryKey(BindRolePermission record);
}