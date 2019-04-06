package com.bst.pidms.controller;

import com.bst.pidms.entity.Permission;
import com.bst.pidms.entity.Role;
import com.bst.pidms.entity.User;
import com.bst.pidms.service.RoleService;
import com.bst.pidms.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Author: BST
 * @Date: 2019/4/2 10:23
 */
@Controller
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @RequestMapping(value = "/usermanage")
    public String getUser(Map<String, Object> map) {
        List<User> users = userService.getUsers();
        List<Role> roles = roleService.getRoles();
        Map<Integer, String> rolesMap = roles.stream().collect(Collectors.toMap(Role::getId, Role::getName));
        map.put("usersList", users);
        map.put("roles", rolesMap);
        map.put("rolesList", roles);
        return "user";
    }


    @RequestMapping(value = "/adduser", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> addUser(@RequestParam("name") String username, @RequestParam("smtp") String smtp, @RequestParam("pop3") String pop3, @RequestParam("acc") String acc, @RequestParam("pwd") String pwd, @RequestParam("roleid") Integer roldId) {
        Map<String, Object> map = new HashMap<String, Object>();
        User user = new User();
        user.setUsername(username);
//      默认密码
        user.setPassword("123456");
        user.setMailAcc(acc);
        user.setMailPwd(pwd);
        user.setSmtp(smtp);
        user.setPop3(pop3);
        user.setRoleId(roldId);
        if (userService.getUserByName(username) != null) {
            map.put("msg", "已存在该用户!");
            map.put("success", false);
        } else {
            userService.addUser(user);
            map.put("result", user.getId());
            map.put("success", true);
        }
        return map;
    }

    @RequestMapping(value = "/deluser", method = RequestMethod.DELETE)
    @ResponseBody
    public String delUser(@RequestParam("id") String id) {
        userService.delUser(Integer.parseInt(id));
        return "success";
    }

    @RequestMapping(value = "modifyuser", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> modifyUser(@RequestParam("id") Integer id, @RequestParam("name") String username, @RequestParam("smtp") String smtp, @RequestParam("pop3") String pop3, @RequestParam("acc") String acc, @RequestParam("pwd") String pwd, @RequestParam("roleid") Integer roldId) {
        Map<String, Object> map = new HashMap<String, Object>();
        if (userService.getUserByName(username) != null) {
            map.put("msg", "已存在该用户!");
            map.put("success", false);
        } else {
            User user = userService.getUserById(id);
            user.setUsername(username);
            user.setMailAcc(acc);
            user.setMailPwd(pwd);
            user.setSmtp(smtp);
            user.setPop3(pop3);
            user.setRoleId(roldId);
            userService.modifyUser(user);
            map.put("success", true);
        }
        return map;
    }



}
