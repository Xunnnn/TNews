package com.zr.service.impl;

import com.zr.dao.UserDao;
import com.zr.po.User;
import com.zr.service.UserService;
import com.zr.util.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public User checkUser(String username, String password) {
        return userDao.findByUsernameAndPassword(username, MD5Util.code(password));
    }
}
