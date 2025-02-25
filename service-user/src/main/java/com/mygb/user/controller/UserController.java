/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.mygb.user.controller;

import java.time.Duration;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mygb.api.dto.user.CreateUserDTO;
import com.mygb.api.dto.user.UserRespDTO;
import com.mygb.user.entity.Role;
import com.mygb.user.entity.User;
import com.mygb.user.mapper.RoleMapper;
import com.mygb.user.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    RedisTemplate<String, Object> redisTemplate;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public String createUser(@Valid @RequestBody CreateUserDTO userDto) {
        // User user = new User();
        // user.setNickname(userDto.getNickname());
        // user.setEmail(userDto.getEmail());
        // user.setPhone(userDto.getPhone());
        // user.setPassword(userDto.getPassword());
        User user = modelMapper.map(userDto, User.class);
        userService.createUser(user);
        return "User created successfully!";
    }

    @GetMapping("/{id}")
    public UserRespDTO getUserById(@PathVariable Long id) {
        User user = userService.getUserById(id);
        UserRespDTO userDTO = modelMapper.map(user, UserRespDTO.class);

        QueryWrapper<Role> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", id); // where user_id = #{id},
        List<Role> roles = roleMapper.selectList(queryWrapper);
        userDTO.setRoles(roles.stream().map(Role::getRoleName).toList());

        return userDTO;
    }

    @GetMapping("/{page}/{size}")
    public Page<User> getUsers(@PathVariable int page, @PathVariable int size) {
        Page<User> pageUsers = userService.getPageUsers(page, size);

        // Page<UserRespDTO> pageUserRespDTO = new Page<>(page, size,
        // pageUsers.getTotal());
        // List<UserRespDTO> records = pageUsers.getRecords().stream()
        // .map(user -> modelMapper.map(user, UserRespDTO.class)).toList();
        // pageUserRespDTO.setRecords(records);
        return pageUsers;
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody UserRespDTO userDto) {
        // 限流, 操作 redis 示例
        ValueOperations<String, Object> valueOps = redisTemplate.opsForValue();
        if (valueOps.get("user:" + id) != null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("too many requests");
        }

        User user = modelMapper.map(userDto, User.class);
        user.setId(id);
        if (userService.updateUser(user) == 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("id not exist");
        }
        valueOps.set("user:" + id, 1, Duration.ofSeconds(30));

        return ResponseEntity.ok("User updated successfully!");
    }

    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return "User deleted successfully!";
    }

}