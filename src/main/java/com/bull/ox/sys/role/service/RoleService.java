package com.bull.ox.sys.role.service;

import com.bull.ox.sys.role.dao.RoleMapper;
import com.bull.ox.sys.role.entity.Role;
import com.bull.ox.sys.user.dao.UserMapper;
import com.bull.ox.sys.user.entity.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService {

    @Autowired
    private RoleMapper roleMapper;

    public Role findById(Long id){
        return roleMapper.findById(id);
    }

    public List<Role> findRolesByUserId(String userName){
        return roleMapper.findRolesByUserId(userName);
    }

}
