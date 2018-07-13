package com.bull.ox.sys.role.dao;

import com.bull.ox.sys.role.entity.Role;
import com.bull.ox.sys.user.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface RoleMapper {
    @Select("SELECT * FROM role WHERE id = #{id}")
    Role findById(@Param("id") Long id);
}
