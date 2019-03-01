package com.longge.cloud.business.authcenter.auth.mobile.provider;
import com.longge.cloud.business.authcenter.auth.mobile.token.MobileCodeAuthenticationToken;
import com.longge.cloud.business.authcenter.auth.mobile.userservice.MobileUserDetailService;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import lombok.Setter;
/**
 * @author: jianglong
 * @description:自定义手机验证码登录验证方法
 * @date: 2019-01-28
 * */
public class MobileCodeAuthenticationProvider implements AuthenticationProvider {

	@Setter
    private MobileUserDetailService mobileUserDetailService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        //这个authentication就是MobileCodeAuthenticationToken
    	MobileCodeAuthenticationToken authenticationToken = (MobileCodeAuthenticationToken) authentication;
    	//手机号
    	 String mobile = (String) authenticationToken.getPrincipal();
        //校验手机号
        UserDetails user = mobileUserDetailService.loadUserByUsername(mobile);
        if (user == null) {
            throw new InternalAuthenticationServiceException("无法获取用户信息");
        }
        //这时候已经认证成功了
        MobileCodeAuthenticationToken authenticationResult = new MobileCodeAuthenticationToken(user, user.getAuthorities());
        authenticationResult.setDetails(authenticationToken.getDetails());
        return authenticationResult;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        //该MobileCodeAuthenticationProvider智支持MobileCodeAuthenticationToken的token认证
        return MobileCodeAuthenticationToken.class.isAssignableFrom(authentication);
    }

}
