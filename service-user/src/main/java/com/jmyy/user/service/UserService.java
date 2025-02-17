/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.jmyy.user.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.jmyy.user.entity.User;
import com.jmyy.user.mapper.UserMapper;

@Service
public class UserService {
    private final UserMapper userMapper;

    public UserService(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    public int createUser(User u) {
        u.setCreatedAt(LocalDateTime.now()); // 设置默认创建时间
        return userMapper.insert(u);
    }

    public User getUserById(Long id) {
        return userMapper.selectUserById(id);
    }

    public List<User> getAllUsers() {
        return userMapper.getUsersByPage(1, 10);
    }

    public int updateUser(User user) {
        return userMapper.updateUser(user);
    }

    public int deleteUser(Long id) {
        return userMapper.delete(id);
    }
}