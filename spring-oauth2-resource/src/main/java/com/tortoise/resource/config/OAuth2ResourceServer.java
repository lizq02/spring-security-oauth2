package com.tortoise.resource.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.RemoteTokenServices;

/**
 * @description: 资源服务器
 * @author: lizhongqing
 * @create: 2022/07/18
 */
@Configuration
@EnableResourceServer // 标识为资源服务器, 所有发往当前服务的请求，都会去请求头里找token，找不到或 验证不通过不允许访问
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class OAuth2ResourceServer extends ResourceServerConfigurerAdapter {
    /*@Resource
    private DataSource dataSource;

    @Bean
    public TokenStore tokenStore() {
        return new JdbcTokenStore(dataSource);
    }
    */

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .anyRequest().authenticated()
                .and()
                .requestMatchers()
                .antMatchers("/test/**");
    }

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        resources.resourceId("oauth2-resource")
                .tokenServices(tokenServices());
    }

    /**
     * 配置资源服务器如何校验token
     * 1. DefaultTokenServices
     * 如果认证服务器和资源服务器在同一个服务，则直接采用默认服务验证
     * 2.RemoteTokenServices
     * 当认证服务器和资源服务器不在同一个服务，要使用此服务器去远程认证服务器验证
     *
     * @return
     */
    @Primary
    @Bean
    public RemoteTokenServices tokenServices() {
        // 资源服务器去远程认证服务器验证 token 是否有效
        final RemoteTokenServices tokenService = new RemoteTokenServices();
        // 请求认证服务器验证URL，注意：默认这个端点是拒绝访问的，要设置认证后可访问
        tokenService.setCheckTokenEndpointUrl("http://localhost:8899/oauth/check_token");
        // 在认证服务器配置的客户端id
        tokenService.setClientId("test-pc");
        // 在认证服务器配置的客户端密码
        tokenService.setClientSecret("123456");
        return tokenService;
    }
}