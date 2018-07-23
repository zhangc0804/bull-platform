package com.bull.ox.sys.user.dao;

import com.bull.ox.sys.user.entity.User;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserMapper {
    @Select("SELECT * FROM user WHERE id = #{id}")
    User findById(@Param("id") Long id);

    @Select("SELECT * FROM user WHERE username = #{username} and password= #{password}")
    User findByUsernamePassword(@Param("username") String username,@Param("password") String password);

    @Insert("insert into user(username,password,name) values(#{user.username},#{user.password},#{user.name})")
    void insert(@Param("user") User user);
    @Delete("delete from user where id=#{id}")
    void delete(@Param("id") Long id);
    @Update("update user u set u.username=#{user.username},u.password=#{user.password},u.name=#{user.name} where u.id=#{user.id}")
    void update(@Param("user") User user);
    @Select("SELECT * FROM user WHERE id = #{id}")
    User query(@Param("id") Long id);
}
