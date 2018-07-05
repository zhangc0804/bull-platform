package com.bull.ox.config.db;


import com.alibaba.druid.filter.Filter;
import com.alibaba.druid.filter.logging.Slf4jLogFilter;
import com.alibaba.druid.filter.stat.StatFilter;
import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.wall.WallFilter;
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

    @Bean(name = "dataSource")
    public DataSource druidDataSource(){
        DruidDataSource datasource = new DruidDataSource();

        datasource.setUrl(environment.getProperty("datasource.url"));
        datasource.setDriverClassName(environment.getProperty("datasource.driver-class-name"));
        datasource.setUsername(environment.getProperty("datasource.username"));
        datasource.setPassword(environment.getProperty("datasource.password"));

        datasource.setInitialSize(Integer.valueOf(environment.getProperty("datasource.initialSize")));
        datasource.setMinIdle(Integer.valueOf(environment.getProperty("datasource.minIdle")));
        datasource.setMaxWait(Long.valueOf(environment.getProperty("datasource.maxWait")));
        datasource.setMaxActive(Integer.valueOf(environment.getProperty("datasource.maxActive")));
        datasource.setMinEvictableIdleTimeMillis(Long.valueOf(environment.getProperty("datasource.minEvictableIdleTimeMillis")));
        List<Filter> filters = new ArrayList();
        filters.add(this.statFilter());
//        filters.add(this.wallFilter());
//        filters.add(this.slf4jLogFilter());
        datasource.setProxyFilters(filters);

        return datasource;
    }

    @Bean
    public StatFilter statFilter() {
        StatFilter statFilter = new StatFilter();
        statFilter.setMergeSql(Boolean.valueOf(environment.getProperty("druid.statFilter.slowSqlMillis")));
        statFilter.setSlowSqlMillis(Long.valueOf(environment.getProperty("druid.statFilter.logSlowSql")));
        statFilter.setLogSlowSql(Boolean.valueOf(environment.getProperty("druid.statFilter.mergeSql")));
        return statFilter;
    }

//    @Bean
//    public WallFilter wallFilter() {
//        WallFilter wallFilter = new WallFilter();
//        wallFilter.setLogViolation(true);
//        wallFilter.setThrowException(false);
//        wallFilter.setConfig(this.wallConfig());
//        return wallFilter;
//    }

//    @Bean
//    public Slf4jLogFilter slf4jLogFilter() {
//        Slf4jLogFilter slf4jLogFilter = new Slf4jLogFilter();
//        return slf4jLogFilter;
//    }

    @Bean
    public ServletRegistrationBean statViewServlet(){
        ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean();
        servletRegistrationBean.setServlet(new StatViewServlet());
        List<String> urlMappings = new ArrayList<>();
        urlMappings.add("/druid/*");
        servletRegistrationBean.setUrlMappings(urlMappings);
        Map<String,String> initParameters = new HashMap<>();
        String resetEnableStr = environment.getProperty("druid.statViewServlet.resetEnable");
        if(resetEnableStr!=null && !"".equals(resetEnableStr)){
            initParameters.put("resetEnable",resetEnableStr);
        }

        initParameters.put("loginUsername",environment.getProperty("druid.statViewServlet.loginUsername"));
        initParameters.put("loginPassword",environment.getProperty("druid.statViewServlet.loginPassword"));
        initParameters.put("allow",environment.getProperty("druid.statViewServlet.allow"));
        initParameters.put("deny",environment.getProperty("druid.statViewServlet.deny"));
        servletRegistrationBean.setInitParameters(initParameters);
        return servletRegistrationBean;
    }
}
