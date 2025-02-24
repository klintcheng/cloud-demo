/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.mygb.user.mapper;

import static com.mygb.user.mapper.UserSqlSupport.createdAt;
import static com.mygb.user.mapper.UserSqlSupport.email;
import static com.mygb.user.mapper.UserSqlSupport.id;
import static com.mygb.user.mapper.UserSqlSupport.nickname;
import static com.mygb.user.mapper.UserSqlSupport.phone;
import static com.mygb.user.mapper.UserSqlSupport.user;
import static org.mybatis.dynamic.sql.SqlBuilder.isEqualTo;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.UpdateProvider;
import org.mybatis.dynamic.sql.SqlBuilder;
import org.mybatis.dynamic.sql.render.RenderingStrategies;
import org.mybatis.dynamic.sql.select.render.SelectStatementProvider;
import org.mybatis.dynamic.sql.update.render.UpdateStatementProvider;
import org.mybatis.dynamic.sql.util.SqlProviderAdapter;
import org.mybatis.dynamic.sql.util.mybatis3.CommonSelectMapper;
import org.mybatis.dynamic.sql.util.mybatis3.CommonUpdateMapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mygb.user.entity.User;

@Mapper
public interface UserMapper extends BaseMapper<User>, CommonSelectMapper, CommonUpdateMapper {

    // 更新用户name
    @UpdateProvider(type = SqlProviderAdapter.class, method = "update")
    default int updateUserName(long uid, String name) {
        UpdateStatementProvider updateStatement;
        updateStatement = SqlBuilder.update(user)
                .set(nickname).equalToWhenPresent(name)
                .where(id, isEqualTo(uid))
                .build()
                .render(RenderingStrategies.MYBATIS3);

        return update(updateStatement);
    }

    default List<User> getUsersByPage(int page, int pageSize) {
        int offset = (page - 1) * pageSize; // 计算 OFFSET

        SelectStatementProvider selectStatement = SqlBuilder.select(id, nickname, email, phone, createdAt)
                .from(user)
                .orderBy(id.descending()) // 按 id 倒序排列
                .limit(pageSize) // 设置分页大小Í
                .offset(offset) // 设置偏移量
                .build()
                .render(RenderingStrategies.MYBATIS3);

        return selectMany(selectStatement, row -> {
            User user = new User();
            user.setId((Long) row.get("id"));
            user.setNickname((String) row.get("nickname"));
            user.setEmail((String) row.get("email"));
            return user;
        });
    }

    // 删除用户
    @Delete("DELETE FROM user WHERE id = #{id}")
    int delete(@Param("id") Long id);
}