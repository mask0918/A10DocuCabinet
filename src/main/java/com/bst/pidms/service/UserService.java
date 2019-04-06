package com.bst.pidms.service;

import com.bst.pidms.entity.Role;
import com.bst.pidms.entity.User;

import java.util.List;

/**
 * @Author: BST
 * @Date: 2019/4/1 21:42
 */
public interface UserService {
    List<User> getUsers();

    void addUser(User user);

    User getUserById(Integer id);

    User getUserByName(String name);

    void delUser(Integer id);

    void modifyUser(User user);


}
