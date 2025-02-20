/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.jmyy.user.controller;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
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

import com.jmyy.api.dto.user.UserDTO;
import com.jmyy.user.entity.User;
import com.jmyy.user.service.UserService;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    @Autowired
    private ModelMapper modelMapper;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public String createUser(@RequestBody UserDTO userDto) {
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
    public User getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody UserDTO userDto) {
        User user = modelMapper.map(userDto, User.class);
        user.setId(id);
        if (userService.updateUser(user) == 0) {
            return ResponseEntity.status(HttpStatus.NOT_MODIFIED).body("id not exist");
        }
        return ResponseEntity.ok("User updated successfully!");
    }

    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return "User deleted successfully!";
    }

}