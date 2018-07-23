package com.bull.ox.sys.user.service;

import com.bull.ox.sys.resource.entity.Resource;
import com.bull.ox.sys.user.dao.UserMapper;
import com.bull.ox.sys.user.entity.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    public User findById(Long id){
        return userMapper.findById(id);
    }

    public User findByUsernamePassword(String username,String password){
        return userMapper.findByUsernamePassword(username,password);
    }

    public void insert(User user){
        userMapper.insert(user);
    }

    public void delete(Long id){
        userMapper.delete(id);
    }

    public void update(User user){
        userMapper.update(user);
    }

    public User query(Long id){
        return userMapper.query(id);
    }

}
