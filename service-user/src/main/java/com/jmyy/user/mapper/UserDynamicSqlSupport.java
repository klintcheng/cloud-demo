/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.jmyy.user.mapper;

import java.time.LocalDateTime;

import org.mybatis.dynamic.sql.AliasableSqlTable;
import org.mybatis.dynamic.sql.SqlColumn;

// 创建 UserDynamicSqlSupport 类
public final class UserDynamicSqlSupport {

    // 定义表名
    public static final User user = new User();

    // 定义每个字段
    public static final SqlColumn<Long> id = user.id;
    public static final SqlColumn<String> nickname = user.nickname;
    public static final SqlColumn<String> email = user.email;
    public static final SqlColumn<String> phone = user.phone;
    public static final SqlColumn<String> password = user.password;
    public static final SqlColumn<LocalDateTime> createdAt = user.createdAt;

    // 定义 User 表
    public static final class User extends AliasableSqlTable<User> {
        public final SqlColumn<Long> id = column("id");
        public final SqlColumn<String> nickname = column("nickname");
        public final SqlColumn<String> email = column("email");
        public final SqlColumn<String> phone = column("phone");
        public final SqlColumn<String> password = column("password");
        public final SqlColumn<LocalDateTime> createdAt = column("created_at");

        public User() {
            super("user", User::new); // 表名
        }
    }
}