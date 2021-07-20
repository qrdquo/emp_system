package com.awei.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;


@Data
@AllArgsConstructor
@Accessors(chain = true)   //设置这个在setter的时候返回对象
public class Emp implements Serializable {
    private String id;
    private String name;
    private String path;
    private double salary;
    private Integer age;
}
