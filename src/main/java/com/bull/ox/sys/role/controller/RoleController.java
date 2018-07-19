package com.bull.ox.sys.role.controller;

import com.bull.ox.sys.role.entity.Role;
import com.bull.ox.sys.role.service.RoleService;
import com.bull.ox.sys.user.entity.User;
import com.bull.ox.sys.user.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class RoleController {

    @Autowired
    private RoleService roleService;

    @GetMapping(path = "/role/{id}")
    public Role findById(@PathVariable Long id){
        return roleService.findById(id);
    }

    @RequiresPermissions("user:create")
    @GetMapping(path = "/roles/{userName}")
    public List<Role> findRolesByUserId(@PathVariable String userName){
        return roleService.findRolesByUserId(userName);
    }

    @GetMapping(path = "/hasrole/{userId}")
    public Map check(@PathVariable Long userId){
        Map result = new HashMap();
        Subject subject = SecurityUtils.getSubject();
        System.out.println(subject.hasRole("系统管理员"));
        System.out.println(subject.hasRole("角色1"));

        System.out.println(subject.isPermitted("user:delete"));
        System.out.println(subject.isPermitted("user:delete2"));
        return result;
    }
}
