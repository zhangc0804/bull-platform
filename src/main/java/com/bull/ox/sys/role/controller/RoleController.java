package com.bull.ox.sys.role.controller;

import com.bull.ox.sys.role.entity.Role;
import com.bull.ox.sys.role.service.RoleService;
import com.bull.ox.sys.user.entity.User;
import com.bull.ox.sys.user.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path="/role")
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

    @PostMapping(path = "/insert", produces = MediaType.APPLICATION_JSON_VALUE)
    public Map insert(Role role){
        Map<String,Object> result = new HashMap<>();
        roleService.insert(role);
        result.put("msg","保存成功");
        return result;
    }

    @DeleteMapping(path = "/delete/{id}")
    public Map delete(@PathVariable("id") Long id){
        Map<String,Object> result = new HashMap<>();
        roleService.delete(id);
        result.put("msg","删除成功");
        return result;
    }

    @PutMapping(path = "/update", produces = MediaType.APPLICATION_JSON_VALUE)
    public Map update(Role role){
        Map<String,Object> result = new HashMap<>();
        roleService.update(role);
        result.put("msg","修改成功");
        return result;
    }

    @GetMapping(path = "/query/{id}")
    public Map query(@PathVariable("id") Long id){
        Map<String,Object> result = new HashMap<>();
        Role role = roleService.query(id);
        result.put("msg","查询成功");
        result.put("data",role);
        return result;
    }

    @PostMapping(path = "/insert/role/relation")
    public Map insertRoleResourceRelations(Long roleId,String resourceIds){
        Map<String,Object> result = new HashMap<>();
        roleService.insertRoleResourceRelations(roleId,resourceIds);
        result.put("msg","保存成功");
        return result;
    }
}
