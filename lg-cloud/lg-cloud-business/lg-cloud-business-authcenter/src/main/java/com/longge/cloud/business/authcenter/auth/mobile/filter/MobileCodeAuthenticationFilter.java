package com.longge.cloud.business.authcenter.auth.mobile.filter;
import com.longge.cloud.business.authcenter.auth.mobile.token.MobileCodeAuthenticationToken;
import com.longge.cloud.business.authcenter.constant.SecurityConstant;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/**
 * @author: jianglong
 * @description: mobile登录拦截器封装Token
 * @date: 2019-01-28
 * */
public class MobileCodeAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    /**是否只处理post请求*/
    private boolean postOnly = true;

    public MobileCodeAuthenticationFilter() {
        //要拦截的请求
        super(new AntPathRequestMatcher(SecurityConstant.MOBILE_LOGINURL, "POST"));
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        if (this.postOnly && !request.getMethod().equals("POST")) {
            throw new AuthenticationServiceException("手机登录不支持: " + request.getMethod());
        } else {
            String mobile = this.obtainMobile(request);
            if (mobile == null) {
                mobile = "";
            }
            mobile = mobile.trim();
            //把手机号传进MobileCodeAuthenticationToken
            MobileCodeAuthenticationToken authRequest = new MobileCodeAuthenticationToken(mobile);
            this.setDetails(request, authRequest);
            //调用AuthenticationManager
            return this.getAuthenticationManager().authenticate(authRequest);
        }
    }
    /**获取手机号参数*/
    private String obtainMobile(HttpServletRequest request) {
        return request.getParameter(SecurityConstant.MOBILE_PARAM);
    }

    private void setDetails(HttpServletRequest request, MobileCodeAuthenticationToken authRequest) {
        authRequest.setDetails(this.authenticationDetailsSource.buildDetails(request));
    }
}
