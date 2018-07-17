package com.bull.ox.sys.role.dao;

import com.bull.ox.sys.role.entity.Role;
import com.bull.ox.sys.user.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface RoleMapper {
    @Select("SELECT * FROM role WHERE id = #{id}")
    Role findById(@Param("id") Long id);

    @Select("select r.* from role r,user u,user_role ur where u.id=ur.user_id and r.id=ur.role_id and u.userName=#{userName}")
    List<Role> findRolesByUserId(@Param("userName") String userName);
}
