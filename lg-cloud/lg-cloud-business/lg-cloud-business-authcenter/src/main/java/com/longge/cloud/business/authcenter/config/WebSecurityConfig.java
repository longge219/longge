package com.longge.cloud.business.authcenter.config;
import com.longge.cloud.business.authcenter.auth.mobile.config.MobileCodeAuthenticationSecurityConfig;
import com.longge.cloud.business.authcenter.auth.mobile.filter.SmsCodeFilter;
import com.longge.cloud.business.authcenter.handler.CusAuthenticationFailureHandler;
import com.longge.cloud.business.authcenter.handler.CusAuthenticationSuccessHandler;
import com.longge.cloud.business.authcenter.service.CodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
@Order(100)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
    @Autowired
    private MobileCodeAuthenticationSecurityConfig mobileCodeAuthenticationSecurityConfig;
    
    @Autowired
    private CusAuthenticationSuccessHandler cusAuthenticationSuccessHandler;
    
    @Autowired
    private CusAuthenticationFailureHandler cusAuthenticationFailureHandler;
    
    @Autowired
    private CodeService codeServiceImpl;

    
  @Override
  @Bean
  public AuthenticationManager authenticationManagerBean() throws Exception {
      return super.authenticationManagerBean();
  }
    
    @Override
    protected void configure(HttpSecurity http) throws Exception {
    	//验证码过滤器
    	SmsCodeFilter smsCodeFilter = new SmsCodeFilter(codeServiceImpl,cusAuthenticationFailureHandler);
        smsCodeFilter.afterPropertiesSet();
        http.addFilterBefore(smsCodeFilter, UsernamePasswordAuthenticationFilter.class)
                //表单登录,loginPage为登录请求的url,loginProcessingUrl为表单登录处理的URL
                .formLogin().loginPage("/authentication/require").loginProcessingUrl("/authentication/form")
                //登录成功之后的处理
                .successHandler(cusAuthenticationSuccessHandler)
                //允许访问
                .and().authorizeRequests().antMatchers(
                		"/authentication/form",
                		"/authentication/require",
                		"/oauthLogin",
                		"/oauthGrant",
                "/logout",
                "/code/sms")
                // "/oauth/**")
                .permitAll().anyRequest().authenticated()
                //禁用跨站伪造
                .and().csrf().disable()
                //短信验证码配置
                .apply(mobileCodeAuthenticationSecurityConfig);
                //账号登录
                //.and().apply(mySocialSecurityConfig);
    }
}