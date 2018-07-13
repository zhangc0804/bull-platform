package com.bull.ox.sys.user.dao;

import com.bull.ox.sys.user.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {
    @Select("SELECT * FROM user WHERE id = #{id}")
    User findById(@Param("id") Long id);

    @Select("SELECT * FROM user WHERE username = #{username} and password= #{password}")
    User findByUsernamePassword(@Param("username") String username,@Param("password") String password);
}
