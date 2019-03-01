package com.longge.cloud.business.authcenter.config;
import com.longge.cloud.business.authcenter.code.CusBCryptPasswordEncoder;
import com.longge.cloud.business.authcenter.redis.store.CusRedisTokenStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
/**
 * @author: jianglong
 * @description:授权服务配置
 * @date: 2019-01-14
 * */
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {
    
    @Autowired
    AuthenticationManager authenticationManager;
    
    @Autowired
    RedisConnectionFactory redisConnectionFactory;
    
    @Autowired
    private ClientDetailsService baseClientDetailService;
    
    @Bean
	public CusRedisTokenStore redisTokenStore() {
    	return new CusRedisTokenStore(redisConnectionFactory);
	}
    
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new CusBCryptPasswordEncoder();
    }
    
    /**
     * ClientDetailsServiceConfigurer--用来配置客户端详情服务（ClientDetailsService）
     * 客户端详情信息在这里进行初始化，你能够把客户端详情信息写死在这里或者是通过数据库来存储调取详情信息
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        //这里通过实现 ClientDetailsService接口
        clients.withClientDetails(baseClientDetailService);
    }
    /**
     * 用来配置授权（authorization）以及令牌（token）的访问端点和令牌服务(token services)
     * /oauth/authorize：授权端点。
     * /oauth/token：令牌端点。
     * /oauth/confirm_access：用户确认授权提交端点。
     * /oauth/error：授权服务错误信息端点。
     * /oauth/check_token：用于资源服务访问的令牌解析端点。
     * /oauth/token_key：提供公有密匙的端点，如果你使用JWT令牌的话
     * */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints  //指定认证管理器
                       .authenticationManager(authenticationManager)
                        //指定token存储位置
                        .tokenStore(redisTokenStore())
                        //refresh_token
                       .reuseRefreshTokens(false)
                       .allowedTokenEndpointRequestMethods(HttpMethod.POST);
    }

    /**用来配置令牌端点(Token Endpoint)的安全约束*/
    @Override
    public void configure(AuthorizationServerSecurityConfigurer oauthServer) throws Exception {
        oauthServer
				       //允许表单认证---如果配置allowFormAuthenticationForClients的，且url中有client_id和client_secret的会走 ClientCredentialsTokenEndpointFilter来保护 
				      //如果没有支持allowFormAuthenticationForClients或者有支持但是url中没有client_id和client_secret的，走basic认证保护
                      .allowFormAuthenticationForClients()
                      // 开启/oauth/token_key验证端口无权限访问
                      .tokenKeyAccess("permitAll()")
                       // 开启/oauth/check_token验证端口认证权限访问
                      .checkTokenAccess("isAuthenticated()");
    }
}