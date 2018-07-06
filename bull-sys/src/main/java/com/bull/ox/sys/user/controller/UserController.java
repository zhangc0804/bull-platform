package com.bull.ox.sys.user.controller;

import com.bull.ox.sys.user.entity.User;
import com.bull.ox.sys.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    private UserService UserService;

    @GetMapping(path = "/user/{id}")
    public User findById(@PathVariable Long id){
        return UserService.findById(id);
    }
}
