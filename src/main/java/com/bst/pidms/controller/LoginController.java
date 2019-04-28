package com.bst.pidms.controller;

import com.bst.pidms.entity.User;
import com.bst.pidms.service.UserService;
import com.bst.pidms.utils.MD5;
import com.bst.pidms.utils.SessionUtil;
import org.apache.catalina.manager.util.SessionUtils;
import org.apache.http.HttpRequest;
import org.apache.http.client.methods.HttpPost;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: BST
 * @Date: 2019/4/26 16:12
 */
@RestController
public class LoginController {
    @Autowired
    UserService userService;

//    @PostMapping("validation")
//    public Map<String, Object> validUser(@RequestParam("username") String name, @RequestParam("password") String pwd, HttpSession httpSession) {
//        Map<String, Object> map = new HashMap<>();
//        User userByName = userService.getUserByName(name);
//        if (userByName == null) {
//            map.put("success", false);
//            map.put("errMsg", "用户不存在!");
//            return map;
//        }
//        if (!userByName.getPassword().equals(MD5.md5(pwd))) {
//            map.put("success", false);
//            map.put("errMsg", "密码错误!");
//            return map;
//        } else map.put("success", true);
//        httpSession.setAttribute("ss", userByName);
//        System.out.println(httpSession.getId());
//        System.out.println(httpSession.toString());
//        return map;
//    }

    @PostMapping("validation")
    public Map<String, Object> validUser(@RequestParam("username") String name, @RequestParam("password") String pwd, HttpSession httpSession, HttpServletResponse response) {
        Map<String, Object> map = new HashMap<>();
        User userByName = userService.getUserByName(name);
        if (userByName == null) {
            map.put("success", false);
            map.put("errMsg", "用户不存在!");
        }
        if (!userByName.getPassword().equals(MD5.md5(pwd))) {
            map.put("success", false);
            map.put("errMsg", "密码错误!");
        } else map.put("success", true);
        SessionUtil.getInstance().setSessionMap(userByName);
        //设置Session过期时间
        SessionUtil.getInstance().setSessionTimeout();
        return map;
    }

    @GetMapping("zz")
    public Map<String, Object> zzz() {
        User user = SessionUtil.getInstance().getUser();
        System.out.println("罗昌文" + user);
        Map<String, Object> map = new HashMap<>();
        map.put("userInfo", user);
        return map;
    }
}
