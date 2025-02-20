package com.jmyy.user.mapper;

import java.sql.JDBCType;

import org.mybatis.dynamic.sql.AliasableSqlTable;
import org.mybatis.dynamic.sql.SqlColumn;

public class RoleSqlSupport {

    // This is the main table definition
    public static final Role role = new Role();

    // Column mappings
    public static final SqlColumn<Long> id = role.id;
    public static final SqlColumn<Long> userId = role.userId;
    public static final SqlColumn<String> roleName = role.roleName;

    // This is the dynamic table definition that extends AliasableSqlTable
    public static final class Role extends AliasableSqlTable<Role> {
        public final SqlColumn<Long> id = column("id", JDBCType.BIGINT);
        public final SqlColumn<Long> userId = column("user_id", JDBCType.INTEGER);
        public final SqlColumn<String> roleName = column("role_name", JDBCType.VARCHAR);

        public Role() {
            super("role", Role::new); // Table name "role"
        }
    }
}
