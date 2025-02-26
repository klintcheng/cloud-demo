package com.mygb.user.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mygb.user.entity.Role;
import com.mygb.user.mapper.RoleMapper;

@Service
public class RoleService {
    private final RoleMapper roleMapper;

    public RoleService(RoleMapper roleMapper) {
        this.roleMapper = roleMapper;
    }

    public List<String> getRolesByUserId(Long userId) {
        QueryWrapper<Role> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId); // where user_id = #{id},
        List<Role> roles = roleMapper.selectList(queryWrapper);
        return roles.stream().map(Role::getRoleName).toList();
    }
}
