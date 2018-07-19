package com.bull.ox.sys.user.controller;

import com.bull.ox.sys.user.entity.User;
import com.bull.ox.sys.user.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.websocket.server.PathParam;
import java.util.HashMap;
import java.util.Map;

@RestController
public class UserController {

    @Autowired
    private UserService UserService;

    @GetMapping(path = "/user/{id}")
    public User findById(@PathVariable Long id){
        return UserService.findById(id);
    }

    @GetMapping(path = "/login")
    public Map login(@RequestParam String username, @RequestParam String password){
        Map<String,Object> result = new HashMap<>();
        Subject subject = SecurityUtils.getSubject();
        try{
            UsernamePasswordToken token = new UsernamePasswordToken(username,password.toCharArray());
            subject.login(token);
            result.put("msg","登录成功");
        }catch(AuthenticationException authenticationException){
            result.put("msg","用户名或密码错误");
        }
        return result;
    }

    @GetMapping(path = "/logout")
    public Map logout(){
        Map<String,Object> result = new HashMap<>();
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        result.put("msg","登出成功");
        return result;
    }
}
