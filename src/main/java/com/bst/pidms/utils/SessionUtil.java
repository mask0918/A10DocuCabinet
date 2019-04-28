package com.bst.pidms.utils;

import com.bst.pidms.entity.User;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.Objects;

/**
 * @Author: BST
 * @Date: 2019/4/27 21:09
 */
public class SessionUtil {
    private static SessionUtil instance;

    private SessionUtil() {
    }

    public static SessionUtil getInstance() {
        synchronized (SessionUtil.class) {
            if (Objects.isNull(instance))
                instance = new SessionUtil();
        }
        return instance;
    }

    private HttpServletRequest getHttpRequest() {
        return ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
    }

    private HttpSession getHttpSession() {
        return getHttpRequest().getSession();
    }

    public Integer getIdNumber() {
        if (isUserLogin())
            return ((User) getHttpSession().getAttribute("user")).getId();
        return -1;
    }

    public User getUser() {
        return (User) getHttpSession().getAttribute("user");
    }

    public boolean isUserLogin() {
        return !Objects.isNull(getHttpSession().getAttribute("user"));
    }

    public boolean isMobile() {
        return getHttpRequest().getHeader("agent") != null && getHttpRequest().getHeader("agent").equals("Android");
    }

    public String getIMEI() {
        return getHttpRequest().getHeader("imei") == null ? "" : getHttpRequest().getHeader("imei");
    }

    public void setSessionMap(User user) {
        //解决JPA懒加载可能导致的permission无法获取的问题
//        user.getRole().setPermissions(Arrays.asList(permissions));
        //不缓存密码
        user.setPassword(null);
        getHttpSession().setAttribute("user", user);
    }

    public void setSessionTimeout() {
        if (isMobile())
            getHttpSession().setMaxInactiveInterval(60 * 60);
        else
            getHttpSession().setMaxInactiveInterval(20 * 60);
    }

    public void logout() {
        getHttpSession().invalidate();
    }
}
