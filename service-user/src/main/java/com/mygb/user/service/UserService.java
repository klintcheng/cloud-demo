/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.mygb.user.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mygb.user.entity.Role;
import com.mygb.user.entity.User;
import com.mygb.user.mapper.RoleMapper;
import com.mygb.user.mapper.UserMapper;

@Service
@EnableCaching
public class UserService {
    private final UserMapper userMapper;

    @Autowired
    private RoleMapper roleMapper;

    public UserService(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    public int createUser(User u) {
        u.setCreatedAt(LocalDateTime.now()); // 设置默认创建时间
        return userMapper.insert(u);
    }

    // 1、Spring 支持在 key 属性中使用 SpEL 表达式来动态生成缓存键
    // @Cacheable(value = "userCache", key = "#user.id + '-' + #user.name")
    // 2、使用 condition 和 unless 属性可以在某些条件下启用或禁用缓存。
    // - condition：只有当条件表达式为 true 时，才会执行缓存。
    // - unless：只有当条件表达式为 true 时，才会阻止缓存。
    // @Cacheable(value = "userCache", key = "#user.id", condition = "#user.id >
    // 10") 只有 user.id > 10 时才会缓存。
    // @Cacheable(value = "userCache", key = "#user.id", unless = "#result == null")
    // 如果返回值为 null，则不缓存。
    // 3、Spring Cache 支持缓存的同步锁，防止在缓存未命中时，多个线程同时访问数据库。在缓存未命中的情况下，默认会进行加锁，直到缓存填充完毕。
    // @Cacheable(value = "userCache", key = "#user.id", sync = true)
    @Cacheable("userCache")
    public User getUserById(Long id) {
        User user = userMapper.selectById(id);
        QueryWrapper<Role> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", id); // where user_id = #{id},
        user.setRoles(roleMapper.selectList(queryWrapper));
        return user;
    }

    public Page<User> getPageUsers(int current, int size) {
        Page<User> page = new Page<>(current, size); // 创建分页对象
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        return userMapper.selectPage(page, queryWrapper);
    }

    @CacheEvict(value = "userCache", key = "#user.id")
    public int updateUser(User user) {
        return userMapper.updateById(user);
    }

    public int deleteUser(Long id) {
        return userMapper.delete(id);
    }
}