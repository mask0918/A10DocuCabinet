package com.bst.pidms.controller.back;

import com.bst.pidms.entity.Permission;
import com.bst.pidms.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: BST
 * @Date: 2019/3/30 20:35
 */
@Controller
public class PermissionController {

    @Autowired
    private PermissionService permissionService;

    @RequestMapping(value = "/permissionmanage")
    public String getPermission(Map<String, Object> map) {
        List<Permission> pers = permissionService.getPers();
        map.put("persList", pers);
        return "permission";
    }

    @RequestMapping(value = "/addper", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> addPermission(@RequestParam("name") String name, @RequestParam("url") String url) {
        Map<String, Object> map = new HashMap<String, Object>();
        Permission permission = new Permission();
        permission.setName(name);
        permission.setUrl(url);
        if (permissionService.getPerByUrl(url) != null || permissionService.getPerByName(name) != null) {
            map.put("msg", "已存在!");
            map.put("success", false);
        } else {
            permissionService.addPer(permission);
            map.put("result", permission.getId());
            map.put("success", true);
        }
        return map;
    }

    @RequestMapping(value = "/delper", method = RequestMethod.DELETE)
    @ResponseBody
    public String delPermission(@RequestParam("id") String id) {
        permissionService.delPer(Integer.parseInt(id));
        return "success";
    }

    @RequestMapping(value = "/modifyper", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> modifyPermission(@RequestParam("id") Integer id, @RequestParam("url") String url, @RequestParam("name") String name) {
        Map<String, Object> map = new HashMap<String, Object>();
        if (permissionService.getPerByUrl(url) != null || permissionService.getPerByName(name) != null) {
            map.put("msg", "已存在");
            map.put("success", false);
        } else {
            Permission permission = new Permission(id, url, name);
            permissionService.modifyPer(permission);
            map.put("success", true);
        }
        return map;
    }


}

