package com.bull.ox.sys.permission.service;

import com.bull.ox.sys.permission.dao.PermissionMapper;
import com.bull.ox.sys.permission.entity.Permission;
import com.bull.ox.sys.role.dao.RoleMapper;
import com.bull.ox.sys.role.entity.Role;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PermissionService {

    @Autowired
    private PermissionMapper permissionMapper;

    public List<Permission> findPermissionsByRoleId(Long roleId){
        return permissionMapper.findPermissionsByRoleId(roleId);
    }

}
