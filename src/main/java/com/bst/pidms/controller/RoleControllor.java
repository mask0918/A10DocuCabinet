package com.bst.pidms.controller;

import com.bst.pidms.entity.BindRolePermission;
import com.bst.pidms.entity.Permission;
import com.bst.pidms.entity.Role;
import com.bst.pidms.entity.User;
import com.bst.pidms.service.BindRolePermissionService;
import com.bst.pidms.service.PermissionService;
import com.bst.pidms.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Author: BST
 * @Date: 2019/4/2 20:24
 */
@Controller
public class RoleControllor {
    @Autowired
    RoleService roleService;
    @Autowired
    PermissionService permissionService;
    @Autowired
    BindRolePermissionService bs;

    @RequestMapping(value = "/rolemanage")
    public String getUser(Map<String, Object> map) {
        List<Role> roles = roleService.getRoles();
        map.put("rolesList", roles);
        Map<Integer, String> persMap = permissionService.getPers().stream().collect(Collectors.toMap(Permission::getId, Permission::getName));
        Map<Integer, List<Integer>> roleMap = new HashMap<Integer, List<Integer>>();
        for (Role role : roles) {
            roleMap.put(role.getId(), bs.getBindByRoleId(role.getId()));
        }
        map.put("persList", permissionService.getPers());
        map.put("perIndex", roleMap);
        map.put("persMap", persMap);
        return "role";
    }

    @RequestMapping(value = "/addrole", method = RequestMethod.POST)
    @ResponseBody
    @Transactional
    public Map<String, Object> addRole(@RequestParam("name") String name, @RequestParam("storage") Integer storage, @RequestParam("indexs") Integer[] indexs) {
        Map<String, Object> map = new HashMap<String, Object>();
        Role role = new Role();
        role.setName(name);
        role.setStorage(storage);
        roleService.addRole(role);
        if (indexs.length > 0) {
//            新增权限关系表
            for (Integer index : indexs) {
                BindRolePermission brp = new BindRolePermission();
                brp.setRoleId(role.getId());
                brp.setPermissionId(index);
                bs.addPer(brp);
            }
        }
        map.put("success", true);
        map.put("result", role.getId());
        return map;
    }

    @RequestMapping(value = "/delrole", method = RequestMethod.DELETE)
    @ResponseBody
    public String delUser(@RequestParam("id") String id) {
        roleService.delRole(Integer.parseInt(id));
        return "success";
    }
}
