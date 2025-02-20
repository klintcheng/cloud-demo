package com.jmyy.user.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

@Data
@TableName("role")
public class Role implements Serializable {
    private Long id;
    private Integer userId;
    private String roleName;
}
