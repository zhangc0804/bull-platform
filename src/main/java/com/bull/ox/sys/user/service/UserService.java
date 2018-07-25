package com.bull.ox.sys.user.service;

import com.bull.ox.common.EncryptUtils;
import com.bull.ox.sys.resource.entity.Resource;
import com.bull.ox.sys.role.entity.Role;
import com.bull.ox.sys.user.dao.UserMapper;
import com.bull.ox.sys.user.entity.User;
import org.apache.ibatis.annotations.Param;
import org.apache.shiro.codec.Hex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    public User findByUsernamePassword(String username, String password) {
        return userMapper.findByUsernamePassword(username, password);
    }

    public void insert(User user) {
        byte[] salt = EncryptUtils.generateSalt();
        user.setSalt(Hex.encodeToString(salt));
        user.setPassword(EncryptUtils.encryptPassword(user.getPassword(),salt));
        userMapper.insert(user);
    }

    public void delete(Long id) {
        userMapper.delete(id);
    }

    public void update(User user) {
        userMapper.update(user);
    }

    public User query(Long id) {
        return userMapper.query(id);
    }

    public void insertUserRoleRelations(Long userId, String roleIds) {
        if (roleIds != null && roleIds.length() > 0) {
            String[] roleIdsStringArray = roleIds.split(",");
            if (roleIdsStringArray != null && roleIdsStringArray.length > 0) {
                for (String roleIdStr : roleIdsStringArray) {
                    long roleId = Long.parseLong(roleIdStr);
                    userMapper.insertUserRoleRelations(userId, roleId);
                }
            }
        }
    }

    public List<User> list() {
        return userMapper.list();
    }

    public List<Role> findRolesByUsername(String username) {
        return userMapper.findRolesByUsername(username);
    }

    public User findByUsername(String username) {
        return userMapper.findByUsername(username);
    }

    public void updatePassword(Long userId,String password){
        User user = userMapper.query(userId);
        byte[] salt = EncryptUtils.generateSalt();
        user.setSalt(Hex.encodeToString(salt));
        user.setPassword(EncryptUtils.encryptPassword(user.getPassword(),salt));
        userMapper.update(user);
    }

}
