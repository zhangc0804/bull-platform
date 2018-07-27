package com.bull.ox.config;

import com.bull.ox.sys.security.realm.SysAuthorizingRealm;
import org.apache.shiro.authz.Authorizer;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.realm.text.TextConfigurationRealm;
import org.apache.shiro.spring.web.config.DefaultShiroFilterChainDefinition;
import org.apache.shiro.spring.web.config.ShiroFilterChainDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ShiroConfig {

    @Autowired
    private SysAuthorizingRealm sysAuthorizingRealm;

    @Bean
    public Realm realm() {
        return sysAuthorizingRealm;
    }

    @Bean
    public Authorizer authorizer() {
        return sysAuthorizingRealm;
    }

    @Bean
    public ShiroFilterChainDefinition shiroFilterChainDefinition() {
        DefaultShiroFilterChainDefinition chainDefinition = new DefaultShiroFilterChainDefinition();

        // logged in users with the 'admin' role
//        chainDefinition.addPathDefinition("/admin/**", "authc, roles[admin]");

        // logged in users with the 'document:read' permission
//        chainDefinition.addPathDefinition("/docs/**", "authc, perms[document:read]");

        // all other paths require a logged in user
//        chainDefinition.addPathDefinition("/**", "authc");

        chainDefinition.addPathDefinition("/login", "anon");
        chainDefinition.addPathDefinition("/login/kaptcha", "anon");
        chainDefinition.addPathDefinition("/logout", "anon");

        // 静态资源
        chainDefinition.addPathDefinition("/framework/**", "anon");
        chainDefinition.addPathDefinition("/css/**", "anon");
        chainDefinition.addPathDefinition("/html/**", "anon");
        chainDefinition.addPathDefinition("/image/**", "anon");
        chainDefinition.addPathDefinition("/js/**", "anon");
        chainDefinition.addPathDefinition("/test/**", "anon");

        // swagger
        chainDefinition.addPathDefinition("/swagger-ui.html", "anon");
        chainDefinition.addPathDefinition("/swagger-resources/**", "anon");
        chainDefinition.addPathDefinition("/v2/api-docs", "anon");
        chainDefinition.addPathDefinition("/webjars/springfox-swagger-ui/**", "anon");

        // druid
        chainDefinition.addPathDefinition("/druid/**", "anon");

        // 验证码
        chainDefinition.addPathDefinition("/kaptcha/generate-kaptcha", "anon");

        chainDefinition.addPathDefinition("/**", "authc");
        return chainDefinition;
    }

}
