package com.bull.ox.config;


import com.alibaba.druid.filter.Filter;
import com.alibaba.druid.filter.logging.Slf4jLogFilter;
import com.alibaba.druid.filter.stat.StatFilter;
import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import com.alibaba.druid.support.spring.stat.DruidStatInterceptor;
import com.alibaba.druid.wall.WallConfig;
import com.alibaba.druid.wall.WallFilter;
import org.springframework.aop.support.JdkRegexpMethodPointcut;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
public class DruidDataSourceConfig implements EnvironmentAware {

    private Environment environment;

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    /**
     * 配置数据源
     *
     * @return
     */
    @Bean("dataSource")
    public DataSource druidDataSource() {
        DruidDataSource datasource = new DruidDataSource();
        // 基本属性
        datasource.setUrl(environment.getProperty("datasource.url"));
        datasource.setDriverClassName(environment.getProperty("datasource.driver-class-name"));
        datasource.setUsername(environment.getProperty("datasource.username"));
        datasource.setPassword(environment.getProperty("datasource.password"));

        // 配置初始化大小、最小、最大
        datasource.setInitialSize(Integer.valueOf(environment.getProperty("datasource.initialSize")));
        datasource.setMinIdle(Integer.valueOf(environment.getProperty("datasource.minIdle")));
        datasource.setMaxActive(Integer.valueOf(environment.getProperty("datasource.maxActive")));
        // 配置获取连接等待超时的时间
        datasource.setMaxWait(Long.valueOf(environment.getProperty("datasource.maxWait")));
        // 配置一个连接在池中最小生存的时间，单位是毫秒
        datasource.setMinEvictableIdleTimeMillis(Long.valueOf(environment.getProperty("datasource.minEvictableIdleTimeMillis")));

        // 连接泄漏监测.配置removeAbandoned对性能会有一些影响，建议怀疑存在泄漏之后再打开。
        // 是否打开removeAbandoned功能
        String removeAbandoned = environment.getProperty("datasource.removeAbandoned");
        if (removeAbandoned != null && !"".equals(removeAbandoned)) {
            datasource.setRemoveAbandoned(Boolean.parseBoolean(removeAbandoned));
        }
        // 如果连接超过指定时间未关闭，就会被强行回收
        String removeAbandonedTimeout = environment.getProperty("datasource.removeAbandonedTimeout");
        if (removeAbandonedTimeout != null && !"".equals(removeAbandonedTimeout)) {
            datasource.setRemoveAbandonedTimeout(Integer.parseInt(removeAbandonedTimeout));
        }
        // 是否关闭abanded连接时输出错误日志
        String logAbandoned = environment.getProperty("datasource.logAbandoned");
        if (logAbandoned != null && !"".equals(logAbandoned)) {
            datasource.setRemoveAbandoned(Boolean.parseBoolean(logAbandoned));
        }

        List<Filter> filters = new ArrayList();
        filters.add(this.statFilter());
        filters.add(this.wallFilter());
        filters.add(this.slf4jLogFilter());
        datasource.setProxyFilters(filters);

        return datasource;
    }

    /**
     * Druid内置StatFilter，用于统计监控信息
     *
     * @return
     */
    @Bean
    public StatFilter statFilter() {
        StatFilter statFilter = new StatFilter();
        // 配置SQL慢的标准
        statFilter.setSlowSqlMillis(Long.valueOf(environment.getProperty("druid.statFilter.slowSqlMillis")));
        // 通过日志输出执行慢的SQL
        statFilter.setLogSlowSql(Boolean.valueOf(environment.getProperty("druid.statFilter.logSlowSql")));
        // SQL合并配置
        statFilter.setMergeSql(Boolean.valueOf(environment.getProperty("druid.statFilter.mergeSql")));
        return statFilter;
    }

    /**
     * wallfilter防御SQL注入攻击
     *
     * @return
     */
    @Bean
    public WallFilter wallFilter() {
        WallFilter wallFilter = new WallFilter();
        String logViolation = environment.getProperty("druid.wallFilter.logViolation");
        if (logViolation != null && !"".equals(logViolation)) {
            wallFilter.setLogViolation(Boolean.parseBoolean(logViolation));
        }
        String throwException = environment.getProperty("druid.wallFilter.throwException");
        if (throwException != null && !"".equals(throwException)) {
            wallFilter.setThrowException(Boolean.parseBoolean(throwException));
        }
        wallFilter.setConfig(this.wallConfig());
        return wallFilter;
    }

    @Bean
    public Slf4jLogFilter slf4jLogFilter() {
        Slf4jLogFilter slf4jLogFilter = new Slf4jLogFilter();
        return slf4jLogFilter;
    }

    /**
     * StatViewServlet用于展示Druid的统计信息
     *
     * @return
     */
    @Bean
    public ServletRegistrationBean statViewServlet() {
        ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean();
        servletRegistrationBean.setServlet(new StatViewServlet());
        List<String> urlMappings = new ArrayList<>();
        urlMappings.add("/druid/*");
        servletRegistrationBean.setUrlMappings(urlMappings);
        Map<String, String> initParameters = new HashMap<>();
        // 在监控页面中，有一个功能是Reset All，执行这个操作之后，会导致所有计数器清零，重新计数。
        String resetEnable = environment.getProperty("druid.statViewServlet.resetEnable");
        if (resetEnable != null && !"".equals(resetEnable)) {
            initParameters.put("resetEnable", resetEnable);
        }
        // 为Druid监控配置访问权限(配置访问监控信息的用户与密码)
        String loginUsername = environment.getProperty("druid.statViewServlet.loginUsername");
        if (loginUsername != null && !"".equals(loginUsername)) {
            initParameters.put("loginUsername", loginUsername);
        }
        String loginPassword = environment.getProperty("druid.statViewServlet.loginPassword");
        if (loginPassword != null && !"".equals(loginPassword)) {
            initParameters.put("loginPassword", loginPassword);
        }
        // 访问控制
        // deny优先于allow，如果在deny列表中，就算在allow列表中，也会被拒绝。
        // 如果allow没有配置或者为空，则允许所有访问
        String allow = environment.getProperty("druid.statViewServlet.allow");
        if (allow != null && !"".equals(allow)) {
            initParameters.put("allow", allow);
        }
        String deny = environment.getProperty("druid.statViewServlet.deny");
        if (deny != null && !"".equals(deny)) {
            initParameters.put("deny", deny);
        }
        servletRegistrationBean.setInitParameters(initParameters);
        return servletRegistrationBean;
    }

    /**
     * WebStatFilter用于采集web-jdbc关联监控的数据
     *
     * @return
     */
    @Bean
    public FilterRegistrationBean filterRegistrationBean() {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        filterRegistrationBean.setFilter(new WebStatFilter());
        filterRegistrationBean.addUrlPatterns("/*");
        // 排除一些不必要的url
        String exclusions = environment.getProperty("druid.webStatFilter.exclusions");
        if (exclusions != null && !"".equals(exclusions)) {
            filterRegistrationBean.addInitParameter("exclusions", exclusions);
        }
        // sessionStatMaxCount配置
        String sessionStatMaxCount = environment.getProperty("druid.webStatFilter.sessionStatMaxCount");
        if (sessionStatMaxCount != null && !"".equals(sessionStatMaxCount)) {
            filterRegistrationBean.addInitParameter("sessionStatMaxCount", sessionStatMaxCount);
        }
        // sessionStatEnable配置，是否关闭session统计功能，默认为true，表示开启
        String sessionStatEnable = environment.getProperty("druid.webStatFilter.sessionStatEnable");
        if (sessionStatEnable != null && !"".equals(sessionStatEnable)) {
            filterRegistrationBean.addInitParameter("sessionStatEnable", sessionStatEnable);
        }
        // principalSessionName配置，使得druid能够知道当前的session的用户是谁
        // 根据需要，把其中的xxx.user修改为你user信息保存在session中的sessionName
        // 如果你session中保存的是非string类型的对象，需要重载toString方法
        String principalSessionName = environment.getProperty("druid.webStatFilter.principalSessionName");
        if (principalSessionName != null && !"".equals(principalSessionName)) {
            filterRegistrationBean.addInitParameter("principalSessionName", principalSessionName);
        }
        // principalCookieName配置，使得druid能够知道当前的session的用户是谁，user信息保存在cookie中
        // 根据需要，把其中的xxx.user修改为你user信息保存在cookie中的cookieName
        String principalCookieName = environment.getProperty("druid.webStatFilter.principalCookieName");
        if (principalCookieName != null && !"".equals(principalCookieName)) {
            filterRegistrationBean.addInitParameter("principalCookieName", principalCookieName);
        }
        // profileEnable配置，配置profileEnable能够监控单个url调用的sql列表。
        String profileEnable = environment.getProperty("druid.webStatFilter.profileEnable");
        if (profileEnable != null && !"".equals(profileEnable)) {
            filterRegistrationBean.addInitParameter("profileEnable", profileEnable);
        }
        return filterRegistrationBean;
    }

    @Bean
    public WallConfig wallConfig() {
        WallConfig wallConfig = new WallConfig();
        String multiStatementAllow = environment.getProperty("druid.wallFilter.multiStatementAllow");
        if (multiStatementAllow != null && !"".equals(multiStatementAllow)) {
            wallConfig.setMultiStatementAllow(Boolean.parseBoolean(multiStatementAllow));
        }
        return wallConfig;
    }


    /**
     * 配置_Druid和Spring关联监控配置
     *
     * @return
     */
    @Bean({"druid-stat-interceptor"})
    public DruidStatInterceptor druidStatInterceptor() {
        DruidStatInterceptor druidStatInterceptor = new DruidStatInterceptor();
        return druidStatInterceptor;
    }

    /**
     * 方法名正则匹配拦截配置
     *
     * @return
     */
    @Bean({"druid-stat-pointcut"})
    public JdkRegexpMethodPointcut jdkRegexpMethodPointcut() {
        JdkRegexpMethodPointcut jdkRegexpMethodPointcut = new JdkRegexpMethodPointcut();
        String patterns = environment.getProperty("druid.stat.pointcut.patterns");
        if (patterns != null && !"".equals(patterns)) {
            jdkRegexpMethodPointcut.setPatterns(patterns.split(","));
        }
        return jdkRegexpMethodPointcut;
    }
}
