package com.bull.ox.sys.user.dao;

import com.bull.ox.sys.user.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {
    @Select("SELECT * FROM users WHERE id = #{id}")
    public User findById(@Param("id") Long id);
}
