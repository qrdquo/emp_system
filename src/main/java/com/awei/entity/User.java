package com.awei.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;


@Data
@AllArgsConstructor        //有参构造
@Accessors(chain = true)   //设置这个setter可以返回操作对象
public class User implements Serializable {
    private String id;  //String 类型的api相对较多
    private String username;
    private String realname;
    private String password;
    private String sex;
    private String status;
    private Date registerTime;
}
