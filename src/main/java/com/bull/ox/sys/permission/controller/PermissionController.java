package com.bull.ox.sys.permission.controller;

import com.bull.ox.sys.permission.entity.Permission;
import com.bull.ox.sys.permission.service.PermissionService;
import com.bull.ox.sys.role.entity.Role;
import com.bull.ox.sys.role.service.RoleService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class PermissionController {

    @Autowired
    private PermissionService permissionService;

    @GetMapping(path = "/permissions/{roleId}")
    public List<Permission> check(@PathVariable Long roleId){
        List<Permission> permissions = permissionService.findPermissionsByRoleId(roleId);
        return permissions;
    }
}
