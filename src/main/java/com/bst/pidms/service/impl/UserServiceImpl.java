package com.bst.pidms.service.impl;

import com.bst.pidms.dao.RoleMapper;
import com.bst.pidms.dao.UserMapper;
import com.bst.pidms.entity.Role;
import com.bst.pidms.entity.User;
import com.bst.pidms.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: BST
 * @Date: 2019/4/1 21:43
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserMapper userMapper;

    @Override
    public List<User> getUsers() {
        return userMapper.selectAll();
    }


    @Override
    public void addUser(User user) {
        userMapper.insert(user);
    }

    @Override
    public User getUserById(Integer id) {
        return userMapper.selectByPrimaryKey(id);
    }

    @Override
    public User getUserByName(String name) {
        return userMapper.selectByName(name);
    }

    @Override
    public void delUser(Integer id) {
        userMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void modifyUser(User user) {
        userMapper.updateByPrimaryKey(user);
    }


}
