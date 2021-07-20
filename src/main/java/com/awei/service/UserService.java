package com.awei.service;

import com.awei.entity.User;

public interface UserService {
    void register(User user);
    User login(User user);
}
