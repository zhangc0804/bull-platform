package com.bull.ox.sys.security.realm;

import com.bull.ox.sys.resource.service.ResourceService;
import com.bull.ox.sys.role.entity.Role;
import com.bull.ox.sys.role.service.RoleService;
import com.bull.ox.sys.user.entity.User;
import com.bull.ox.sys.user.service.UserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("sysAuthorizingRealm")
public class SysAuthorizingRealm extends AuthorizingRealm {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private ResourceService resourceService;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        String username = (String)principals.getPrimaryPrincipal();
        List<Role> roles =roleService.findRolesByUserId(username);
        if(roles!=null && !roles.isEmpty()){
            for (Role role:roles) {
                authorizationInfo.addRole(role.getName());
//                List<Permission> permissions = resourceService.findPermissionsByRoleId(role.getId());
//                if(permissions!=null && !permissions.isEmpty()){
//                    for(Permission permission : permissions){
//                        authorizationInfo.addStringPermission(permission.getDesc());
//                    }
//                }
            }
        }
        return authorizationInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        AuthenticationInfo authenticationInfo = null;
        if(token instanceof UsernamePasswordToken){
            UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken)token;
            String username = usernamePasswordToken.getUsername();
            char[] password= usernamePasswordToken.getPassword();
            User user = userService.findByUsernamePassword(username,new String(password));
            if(user!=null){
                SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(username,password,this.getName());
                authenticationInfo = simpleAuthenticationInfo;
            }
        }
        return authenticationInfo;
    }
}
