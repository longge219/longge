package com.longge.cloud.business.authcenter.auth.base;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.security.oauth2.provider.NoSuchClientException;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.stereotype.Service;
/**
 * @author: jianglong
 * @description:自定义客户端认证
 * @date: 2019-01-14
 * */
@Configuration
@Service("baseClientDetailService")
public class BaseClientDetailService implements ClientDetailsService {

    private static final Logger logger = LoggerFactory.getLogger(BaseClientDetailService.class);
    
    @Override
    public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {
	        BaseClientDetails client = null;
	        //这里可以改为查询数据库
	        if("app".equals(clientId)) {
	        	logger.info("认证的服务端ID是"+clientId);
	            client = new BaseClientDetails();
	            client.setClientId(clientId);
	            client.setClientSecret("app");
	            client.setResourceIds(Arrays.asList("order"));
	            client.setAuthorizedGrantTypes(Arrays.asList("refresh_token", "password"));
	            //不同的client可以通过 一个scope 对应 权限集
	            client.setScope(Arrays.asList("all", "select"));
	            client.setAuthorities(AuthorityUtils.createAuthorityList("admin_role"));
	            client.setAccessTokenValiditySeconds((int)TimeUnit.DAYS.toSeconds(1)); //1天
	            client.setRefreshTokenValiditySeconds((int)TimeUnit.DAYS.toSeconds(1)); //1天
	            Set<String> uris = new HashSet<>();
	            uris.add("http://www.baidu.com");
	            client.setRegisteredRedirectUri(uris);
	        }
	      if(client == null) {
             throw new NoSuchClientException("No client width requested id: " + clientId);
	      }
	        return client;
	    }
}