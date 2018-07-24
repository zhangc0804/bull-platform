package com.bull.ox.sys.role.dao;

import com.bull.ox.sys.resource.entity.Resource;
import com.bull.ox.sys.role.entity.Role;
import com.bull.ox.sys.user.entity.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface RoleMapper {

    @Insert("insert into role(name) values(#{role.name})")
    void insert(@Param("role") Role role);

    @Delete("delete from role where id=#{id}")
    void delete(@Param("id") Long id);

    @Update("update role r set r.name=#{role.name} where r.id=#{role.id}")
    void update(@Param("role") Role role);

    @Select("SELECT * FROM role WHERE id = #{id}")
    Role query(@Param("id") Long id);

    @Insert("insert into role_resource(role_id,resource_id) values(#{roleId},#{resourceId})")
    void insertRoleResourceRelations(@Param("roleId") Long roleId, @Param("resourceId") Long resourceId);

    @Select("select * from role")
    List<Role> list();

    @Select("select res.* from role r left join role_resource rr on rr.role_id=r.id left join resource res on res.id=rr.resource_id where r.id=#{roleId}")
    List<Resource> findResourcesByRoleId(@Param("roleId") Long roleId);
}
