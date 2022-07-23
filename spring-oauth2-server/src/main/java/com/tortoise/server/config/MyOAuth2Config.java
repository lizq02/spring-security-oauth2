package com.tortoise.server.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices;
import org.springframework.security.oauth2.provider.code.JdbcAuthorizationCodeServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;

import javax.sql.DataSource;

/**
 * @description:
 * @author: lizhongqing
 * @create: 2022/07/18
 */
@Configuration
public class MyOAuth2Config {
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * druid数据源
     *
     * @return
     */
    @Bean
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource druidDataSource() {
        return new DruidDataSource();
    }

    /**
     * 使用 JDBC 方式管理客户端信息
     *
     * @return
     */
    @Bean("jdbcClientDetailsService")
    public ClientDetailsService jdbcClientDetailsService() {
        return new JdbcClientDetailsService(druidDataSource());
    }

    /**
     * jdbc管理令牌
     * 步骤：
     * 1.创建相关表
     * 2.添加jdbc相关依赖
     * 3.配置数据源信息
     *
     * @return
     */
    @Bean
    public TokenStore jdbcTokenStore() {
        return new JdbcTokenStore(druidDataSource());
    }

    /**
     * 授权码管理策略
     *
     * @return
     */
    @Bean
    public AuthorizationCodeServices jdbcAuthorizationCodeServices() {
        //使用JDBC方式保存授权码到 oauth_code中
        return new JdbcAuthorizationCodeServices(druidDataSource());
    }
}
