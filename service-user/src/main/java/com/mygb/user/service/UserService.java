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
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mygb.user.entity.Role;
import com.mygb.user.entity.User;
import com.mygb.user.mapper.RoleMapper;
import com.mygb.user.mapper.UserMapper;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
@EnableCaching
public class UserService {

    private final UserMapper userMapper;
    private final RoleMapper roleMapper;

    public UserService(UserMapper userMapper, RoleMapper roleMapper) {
        this.userMapper = userMapper;
        this.roleMapper = roleMapper;
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
        try {
            User user = userMapper.selectById(id);
            return user;
        } catch (IllegalArgumentException e) {
            return null;
        }
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

    @Transactional
    public int deleteUser(Long id) {
        int rows = userMapper.deleteById(id);
        if (rows == 0) {
            return 0;
        }
        // 删除用户后,清除roles
        rows = roleMapper.delete(new QueryWrapper<Role>().eq("user_id", id));
        log.atDebug().log("删除了 {} 条角色", rows);
        if (rows == 0) {
            // transactional 回滚
            throw new RuntimeException("删除角色失败");
        }
        return 1;
    }
}