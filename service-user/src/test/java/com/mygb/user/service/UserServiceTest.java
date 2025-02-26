package com.mygb.user.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mygb.user.entity.Role;
import com.mygb.user.entity.User;
import com.mygb.user.mapper.RoleMapper;
import com.mygb.user.mapper.UserMapper;

@ExtendWith(MockitoExtension.class) // 启动 Mockito 扩展
@MockitoSettings(strictness = Strictness.LENIENT)
public class UserServiceTest {

	@Mock
	private UserMapper userMapper; // 模拟 UserMapper
	@Mock
	private RoleMapper roleMapper;

	@InjectMocks
	private UserService userService; // 被测试的 service 类

	private User user;

	@BeforeEach
	void setUp() {
		// 初始化用户数据
		user = new User();
		user.setId(1L);
		user.setNickname("John Doe");
		user.setEmail("zrGQD@example.com");
		user.setPhone("1234567890");
		user.setPassword("123456");
	}

	@Test
	void testCreateUser() {
		// 模拟 userMapper 插入操作返回影响的行数
		when(userMapper.insert(user)).thenReturn(1);

		int result = userService.createUser(user);

		// 验证 userMapper.insert 方法是否被调用一次
		verify(userMapper, times(1)).insert(user);
		assertEquals(1, result, "User should be created successfully.");
	}

	@Test
	void testDeleteUser() {
		// 模拟 userMapper 删除操作返回影响的行数
		Long userId = 1L;

		when(userMapper.deleteById(userId)).thenReturn(1);
		when(roleMapper.delete(any())).thenReturn(1);

		int result = userService.deleteUser(userId);

		verify(userMapper, times(1)).deleteById(userId);

		assertEquals(1, result, "User should be deleted successfully.");
	}

	@Test
	void testDeleteUserNotFound() {
		// 模拟删除操作没有影响行数，表示删除失败
		Long userId = 999L; // 假设这个 ID 在数据库中不存在
		when(userMapper.deleteById(userId)).thenReturn(0);

		int result = userService.deleteUser(userId);

		// 验证 userMapper.deleteById 方法是否被调用一次
		verify(userMapper, times(1)).deleteById(userId);
		assertEquals(0, result, "No user should be deleted if the user is not found.");
	}
}
