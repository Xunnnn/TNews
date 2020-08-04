package com.zr.service;

import com.zr.po.User;

public interface UserService {

    User checkUser(String username, String password);
}
