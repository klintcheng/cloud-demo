package com.mygb.user.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.mybatis.dynamic.sql.util.mybatis3.CommonSelectMapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mygb.user.entity.Role;

@Mapper
public interface RoleMapper extends BaseMapper<Role>, CommonSelectMapper {

}
