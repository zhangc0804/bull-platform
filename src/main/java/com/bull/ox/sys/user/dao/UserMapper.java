package com.bull.ox.sys.user.dao;

import com.bull.ox.sys.role.entity.Role;
import com.bull.ox.sys.user.entity.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserMapper {
    @Select("SELECT * FROM user WHERE username = #{username} and password= #{password}")
    User findByUsernamePassword(@Param("username") String username, @Param("password") String password);

    @Insert("insert into user(username,password,name,salt) values(#{user.username},#{user.password},#{user.name},#{user.salt})")
    void insert(@Param("user") User user);

    @Delete("delete from user where id=#{id}")
    void delete(@Param("id") Long id);

    @Update("update user u set u.username=#{user.username},u.password=#{user.password},u.name=#{user.name},u.salt=#{user.salt} where u.id=#{user.id}")
    void update(@Param("user") User user);

    @Select("SELECT * FROM user WHERE id = #{id}")
    User query(@Param("id") Long id);

    @Insert("insert into user_role(user_id,role_id) values(#{userId},#{roleId})")
    void insertUserRoleRelations(@Param("userId") Long userId, @Param("roleId") Long roleId);

    @Select("select * from user")
    List<User> list();

    @Select("select r.* from user u left join user_role ur on ur.user_id=u.id left join role r on r.id=ur.role_id where u.username=#{username}")
    List<Role> findRolesByUsername(@Param("username") String username);

    @Select("SELECT * FROM user WHERE username = #{username}")
    User findByUsername(@Param("username") String username);

}
