package com.longge.cloud.business.authcenter.auth.mobile.config;
import com.longge.cloud.business.authcenter.auth.mobile.filter.MobileCodeAuthenticationFilter;
import com.longge.cloud.business.authcenter.auth.mobile.provider.MobileCodeAuthenticationProvider;
import com.longge.cloud.business.authcenter.auth.mobile.userservice.MobileUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;
/**
 * @author: jianglong
 * @description: 手机登录配置
 * @date: 2018-01-20
 * */
@Component
public class MobileCodeAuthenticationSecurityConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    @Autowired
    private AuthenticationSuccessHandler mobileAuthenticationSuccessHandler;
    
    @Autowired
    private MobileUserDetailService mobileUserDetailService;

    @Override
    public void configure(HttpSecurity http) throws Exception {
    	MobileCodeAuthenticationFilter smsCodeAuthenticationFilter = new MobileCodeAuthenticationFilter();
        //设置AuthenticationManager
        smsCodeAuthenticationFilter.setAuthenticationManager(http.getSharedObject(AuthenticationManager.class));
        //设置成功失败处理器
        smsCodeAuthenticationFilter.setAuthenticationSuccessHandler(mobileAuthenticationSuccessHandler);
        //设置provider
        MobileCodeAuthenticationProvider mobileCodeAuthenticationProvider = new MobileCodeAuthenticationProvider();
        mobileCodeAuthenticationProvider.setMobileUserDetailService(mobileUserDetailService);
        http.authenticationProvider(mobileCodeAuthenticationProvider).addFilterAfter(smsCodeAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
    }
}
