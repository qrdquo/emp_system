package com.awei.dao;

import com.awei.entity.Emp;

import java.util.List;


public interface EmpDao {
    List<Emp> findAll();

    void save(Emp emp);
    //删除对应Emp
    void delete(String id);
    //查找员工信息用于删除对应的头像图片
    Emp findOne(String id);

    void update(Emp emp);
}
