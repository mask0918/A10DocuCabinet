package com.bst.pidms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.Map;

@Controller
public class IndexController {
    @Autowired
//    private UserServiceImpl userService;

//    登录页面
    @RequestMapping(value = "/")
    public String index(Map<String, Object> map) {
//        List<User> userList = userService.getUserList();
//        map.put("list", userList);
        return "welcome";
    }

    //    登录流程
    @RequestMapping(value = "/login")
    public String login(@RequestParam("username") String username, @RequestParam("password") String password, Map<String, Object> map, HttpSession httpSession) {
        if ("admin".equals(username) && "123456".equals(password)) {
            httpSession.setAttribute("loginUser", username);
            httpSession.setMaxInactiveInterval(-1);
            return "index";
        } else {
            map.put("msg", "用户名密码错误!");
            return "welcome";
        }
    }


}
