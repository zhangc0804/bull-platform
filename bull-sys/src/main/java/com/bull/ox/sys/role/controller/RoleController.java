package com.bull.ox.sys.role.controller;

import com.bull.ox.sys.role.entity.Role;
import com.bull.ox.sys.role.service.RoleService;
import com.bull.ox.sys.user.entity.User;
import com.bull.ox.sys.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RoleController {

    @Autowired
    private RoleService roleService;

    @GetMapping(path = "/role/{id}")
    public Role findById(@PathVariable Long id){
        return roleService.findById(id);
    }
}
