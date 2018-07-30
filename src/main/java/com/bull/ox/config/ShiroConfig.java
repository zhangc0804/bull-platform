package com.bull.ox.config;

import com.bull.ox.sys.security.realm.SysAuthorizingRealm;
import org.apache.shiro.authz.Authorizer;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.realm.text.TextConfigurationRealm;
import org.apache.shiro.spring.web.config.DefaultShiroFilterChainDefinition;
import org.apache.shiro.spring.web.config.ShiroFilterChainDefinition;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.servlet.SimpleCookie;
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

//    @Bean
//    public SimpleCookie simpleCookie(){
//        //这个参数是cookie的名称，对应前端的checkbox的name = rememberMe
//        SimpleCookie simpleCookie = new SimpleCookie("rememberMe");
//        //记住我cookie生效时间30天 ,单位秒
//        simpleCookie.setMaxAge(30*24*60*60);
//        return simpleCookie;
//    }
//
//    @Bean("rememberMeManager")
//    public CookieRememberMeManager cookieRememberMeManager(){
//        CookieRememberMeManager cookieRememberMeManager = new CookieRememberMeManager();
//        cookieRememberMeManager.setCookie(simpleCookie());
//        //rememberMe cookie加密的密钥 建议每个项目都不一样 默认AES算法 密钥长度(128 256 512 位)
//        cookieRememberMeManager.setCipherKey(Base64.decode("2AvVhdsgUs0FSA3SDFAdag=="));
//        return cookieRememberMeManager;
//    }

    @Bean
    public ShiroFilterChainDefinition shiroFilterChainDefinition() {
        DefaultShiroFilterChainDefinition chainDefinition = new DefaultShiroFilterChainDefinition();

        // logged in users with the 'admin' role
//        chainDefinition.addPathDefinition("/admin/**", "authc, roles[admin]");

        // logged in users with the 'document:read' permission
//        chainDefinition.addPathDefinition("/docs/**", "authc, perms[document:read]");

        // all other paths require a logged in user
//        chainDefinition.addPathDefinition("/**", "authc");

        //配置记住我或认证通过可以访问的地址
        chainDefinition.addPathDefinition("/", "user");

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
