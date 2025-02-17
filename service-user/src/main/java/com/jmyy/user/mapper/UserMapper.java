/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.jmyy.user.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.mybatis.dynamic.sql.SqlBuilder;
import static org.mybatis.dynamic.sql.SqlBuilder.isEqualTo;
import org.mybatis.dynamic.sql.insert.render.InsertStatementProvider;
import org.mybatis.dynamic.sql.render.RenderingStrategies;
import org.mybatis.dynamic.sql.select.SelectDSL;
import org.mybatis.dynamic.sql.select.render.SelectStatementProvider;
import org.mybatis.dynamic.sql.update.render.UpdateStatementProvider;
import org.mybatis.dynamic.sql.util.mybatis3.CommonInsertMapper;
import org.mybatis.dynamic.sql.util.mybatis3.CommonSelectMapper;
import org.mybatis.dynamic.sql.util.mybatis3.CommonUpdateMapper;

import com.jmyy.user.entity.User;
import static com.jmyy.user.mapper.UserDynamicSqlSupport.createdAt;
import static com.jmyy.user.mapper.UserDynamicSqlSupport.email;
import static com.jmyy.user.mapper.UserDynamicSqlSupport.id;
import static com.jmyy.user.mapper.UserDynamicSqlSupport.nickname;
import static com.jmyy.user.mapper.UserDynamicSqlSupport.password;
import static com.jmyy.user.mapper.UserDynamicSqlSupport.phone;
import static com.jmyy.user.mapper.UserDynamicSqlSupport.user;

@Mapper
public interface UserMapper extends CommonInsertMapper<User>, CommonSelectMapper, CommonUpdateMapper {

    default int insert(User u) {
        InsertStatementProvider<User> insertStatement = SqlBuilder.insert(u)
                .into(user)
                .map(nickname).toProperty("nickname")
                .map(password).toProperty("password")
                .map(email).toProperty("email")
                .map(createdAt).toProperty("createdAt")
                .build()
                .render(RenderingStrategies.MYBATIS3);

        return insert(insertStatement);
    }

    default User selectUserById(Long id) {
        // 构建 SelectStatementProvider
        SelectStatementProvider selectStatement = SelectDSL.select(user.id, nickname, email)
                .from(user)
                .where(user.id, isEqualTo(id))
                .build()
                .render(RenderingStrategies.MYBATIS3);

        return selectOne(selectStatement, row -> {
            User user = new User();
            user.setId((Long) row.get("id"));
            user.setNickname((String) row.get("nickname"));
            user.setEmail((String) row.get("email"));
            return user;
        });
    }

    // 更新用户
    // @UpdateProvider(type = SqlProviderAdapter.class, method = "update")
    default int updateUser(User u) {
        UpdateStatementProvider updateStatement;
        updateStatement = SqlBuilder.update(user)
                .set(nickname).equalToWhenPresent(u.getNickname())
                .set(email).equalToWhenPresent(u.getEmail())
                .set(phone).equalToWhenPresent(u.getPhone())
                .set(password).equalToWhenPresent(u.getPassword())
                .set(createdAt).equalToWhenPresent(u.getCreatedAt())
                .where(id, isEqualTo(u.getId()))
                .build()
                .render(RenderingStrategies.MYBATIS3);

        return update(updateStatement);
    }

    default List<User> getUsersByPage(int page, int pageSize) {
        int offset = (page - 1) * pageSize; // 计算 OFFSET

        SelectStatementProvider selectStatement = SqlBuilder.select(id, nickname, email, phone, createdAt)
                .from(user)
                .orderBy(id.descending()) // 按 id 倒序排列
                .limit(pageSize) // 设置分页大小
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