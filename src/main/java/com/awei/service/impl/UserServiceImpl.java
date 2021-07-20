package com.awei.service.impl;

import com.awei.dao.UserDao;
import com.awei.entity.User;
import com.awei.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.Date;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    @Autowired
    UserDao userDao;

    @Override
    public void register(User user) {
        //根据用户输入的用户名判断用户是否存在
        User userDB = userDao.findByUserName(user.getUsername());
        if(userDB==null){
            //1生成用户状态
            user.setStatus("已激活");
            //2设置用户注册时间
            user.setRegisterTime(new Date());
            //3调用dao
            userDao.save(user);
        }else {
            throw new RuntimeException("用户名已存在");
        }
    }

    @Override
    public User  login(User user) {
        //1根据用户输入用户名进行查询
        User userDB = userDao.findByUserName(user.getUsername());
        if(!ObjectUtils.isEmpty(userDB)){
            //2查询密码
            if(user.getPassword().equals(userDB.getPassword())){
               return userDB;
            }else {
                throw new RuntimeException("密码输入错误");
            }
        }else {
            throw new RuntimeException("用户名不存在");
        }
    }
}
