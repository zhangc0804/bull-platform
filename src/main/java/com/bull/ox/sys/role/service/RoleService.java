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

    public void insert(Role role){
        roleMapper.insert(role);
    }

    public void delete(Long id){
        roleMapper.delete(id);
    }

    public void update(Role role){
        roleMapper.update(role);
    }

    public Role query(Long id){
        return roleMapper.query(id);
    }

    public void insertRoleResourceRelations(Long roleId,String resourceIds){
        if(resourceIds!=null && resourceIds.length()>0){
            String[] resourceIdsStringArray = resourceIds.split(",");
            if(resourceIdsStringArray!=null && resourceIdsStringArray.length>0){
                for(String resourceIdStr : resourceIdsStringArray){
                    long resourceId = Long.parseLong(resourceIdStr);
                    roleMapper.insertRoleResourceRelations(resourceId,roleId);
                }
            }
        }
    }
}
