package com.bull.ox.sys.security.realm;

import com.bull.ox.common.EncryptUtils;
import com.bull.ox.sys.resource.entity.Resource;
import com.bull.ox.sys.role.entity.Role;
import com.bull.ox.sys.role.service.RoleService;
import com.bull.ox.sys.user.entity.User;
import com.bull.ox.sys.user.service.UserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.codec.Hex;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component("sysAuthorizingRealm")
public class SysAuthorizingRealm extends AuthorizingRealm {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        String username = (String) principals.getPrimaryPrincipal();
        List<Role> roles = userService.findRolesByUsername(username);
        Set<String> stringRoles = new HashSet<>();
        Set<String> stringPermissions = new HashSet<>();
        if (roles != null && !roles.isEmpty()) {
            for (Role role : roles) {
                stringRoles.add(role.getName());
                List<Resource> resources = roleService.findResourcesByRoleId(role.getId());
                if (resources != null && !resources.isEmpty()) {
                    for (Resource resource : resources) {
                        if (resource.getIdentifier() != null) {
                            stringPermissions.add(resource.getIdentifier());
                        }
                    }
                }
            }
        }
        authorizationInfo.addRoles(stringRoles);
        authorizationInfo.addStringPermissions(stringPermissions);
        return authorizationInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        AuthenticationInfo authenticationInfo = null;
        if (token instanceof UsernamePasswordToken) {
            UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) token;
            String username = usernamePasswordToken.getUsername();
//            char[] password = usernamePasswordToken.getPassword();
            User user = userService.findByUsername(username);
            if (user != null) {
//                SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(username, password, this.getName());
                byte[] salt = Hex.decode(user.getSalt());
                SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(user.getUsername(), user.getPassword(),ByteSource.Util.bytes(salt),this.getName());
                authenticationInfo = simpleAuthenticationInfo;
            }
        }
        return authenticationInfo;
    }

    @Override
    public void setCredentialsMatcher(CredentialsMatcher credentialsMatcher) {
        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
        hashedCredentialsMatcher.setHashAlgorithmName(EncryptUtils.HASH_ALGORITHM);
        hashedCredentialsMatcher.setHashIterations(EncryptUtils.HASH_INTERATIONS);
        super.setCredentialsMatcher(hashedCredentialsMatcher);
    }
}
