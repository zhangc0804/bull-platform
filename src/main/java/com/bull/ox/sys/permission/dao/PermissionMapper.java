package com.bull.ox.sys.permission.dao;

import com.bull.ox.sys.permission.entity.Permission;
import com.bull.ox.sys.role.entity.Role;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface PermissionMapper {

    @Select("select * from permission p,role r,role_permission rp where p.id=rp.permission_id and r.id=rp.role_id and r.id=#{roleId}")
    List<Permission> findPermissionsByRoleId(@Param("roleId") Long roleId);
}
