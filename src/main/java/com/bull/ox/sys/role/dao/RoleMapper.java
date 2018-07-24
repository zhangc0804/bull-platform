package com.bull.ox.sys.role.dao;

import com.bull.ox.sys.role.entity.Role;
import com.bull.ox.sys.user.entity.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface RoleMapper {
    @Select("SELECT * FROM role WHERE id = #{id}")
    Role findById(@Param("id") Long id);

    @Select("select r.* from role r,user u,user_role ur where u.id=ur.user_id and r.id=ur.role_id and u.userName=#{userName}")
    List<Role> findRolesByUserId(@Param("userName") String userName);

    @Insert("insert into role(name) values(#{role.name})")
    void insert(@Param("role") Role role);
    @Delete("delete from role where id=#{id}")
    void delete(@Param("id") Long id);
    @Update("update role r set r.name=#{role.name} where r.id=#{role.id}")
    void update(@Param("role") Role role);
    @Select("SELECT * FROM role WHERE id = #{id}")
    Role query(@Param("id") Long id);
    @Insert("insert into role_resource(role_id,resource_id) values(#{roleId},#{resourceId})")
    void insertRoleResourceRelations(@Param("roleId") Long roleId,@Param("resourceId") Long resourceId);
}
