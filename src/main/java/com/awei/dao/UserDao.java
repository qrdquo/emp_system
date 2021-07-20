package com.awei.dao;

import com.awei.entity.User;



    //用来创建Dao对象
public interface UserDao {
    void save(User user);

    //基于用户名进行查询
    User findByUserName(String username);
}
