package com.bull.ox.sys.user.controller;

import com.bull.ox.sys.resource.entity.Resource;
import com.bull.ox.sys.resource.service.ResourceService;
import com.bull.ox.sys.role.entity.Role;
import com.bull.ox.sys.user.entity.User;
import com.bull.ox.sys.user.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping(path = "/login")
    public Map login(@RequestParam String username, @RequestParam String password) {
        Map<String, Object> result = new HashMap<>();
        Subject subject = SecurityUtils.getSubject();
        try {
            UsernamePasswordToken token = new UsernamePasswordToken(username, password);
            subject.login(token);
            result.put("msg", "登录成功");
        } catch (AuthenticationException authenticationException) {
            result.put("msg", "用户名或密码错误");
        }
        return result;
    }

    @GetMapping(path = "/logout")
    public Map logout() {
        Map<String, Object> result = new HashMap<>();
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        result.put("msg", "登出成功");
        return result;
    }

    @RequiresPermissions("sys:user:create")
    @PostMapping(path = "/user/insert", produces = MediaType.APPLICATION_JSON_VALUE)
    public Map insert(User user) {
        Map<String, Object> result = new HashMap<>();
        userService.insert(user);
        result.put("msg", "保存成功");
        return result;
    }

    @RequiresPermissions("sys:user:delete")
    @DeleteMapping(path = "/user/delete/{id}")
    public Map delete(@PathVariable("id") Long id) {
        Map<String, Object> result = new HashMap<>();
        userService.delete(id);
        result.put("msg", "删除成功");
        return result;
    }

    @RequiresPermissions("sys:user:update")
    @PutMapping(path = "/user/update", produces = MediaType.APPLICATION_JSON_VALUE)
    public Map update(User user) {
        Map<String, Object> result = new HashMap<>();
        userService.update(user);
        result.put("msg", "修改成功");
        return result;
    }

    @RequiresPermissions("sys:user:view")
    @GetMapping(path = "/user/query/{id}")
    public Map query(@PathVariable("id") Long id) {
        Map<String, Object> result = new HashMap<>();
        User user = userService.query(id);
        result.put("msg", "查询成功");
        result.put("data", user);
        return result;
    }

    @PostMapping(path = "/user/insert/role/relation")
    public Map insertUserRoleRelations(Long userId, String roleIds) {
        Map<String, Object> result = new HashMap<>();
        userService.insertUserRoleRelations(userId, roleIds);
        result.put("msg", "保存成功");
        return result;
    }

    @RequiresPermissions("sys:user:list")
    @GetMapping(path = "/user/list")
    public Map list() {
        Map<String, Object> result = new HashMap<>();
        List<User> users = userService.list();
        result.put("msg", "查询列表成功");
        result.put("datas", users);
        return result;
    }

    @GetMapping(path = "/user/roles")
    public Map findRolesByUsername(@RequestParam String username) {
        Map<String, Object> result = new HashMap<>();
        List<Role> roles = userService.findRolesByUsername(username);
        result.put("msg", "查询角色成功");
        result.put("datas", roles);
        return result;
    }

//    @RequiresPermissions("sys:user:update-password")
    @PutMapping(path = "/user/update-password", produces = MediaType.APPLICATION_JSON_VALUE)
    public Map update(Long userId,String password) {
        Map<String, Object> result = new HashMap<>();
        userService.updatePassword(userId,password);
        result.put("msg", "修改成功");
        return result;
    }

    @GetMapping(path = "/hello",consumes = "application/json;charset=UTF-8")
    public Map hello(UserVo userVo) {
        Map<String, Object> result = new HashMap<>();
        System.out.println(userVo.getUsername());
        result.put("msg", "登录成功");
        return result;
    }
}
