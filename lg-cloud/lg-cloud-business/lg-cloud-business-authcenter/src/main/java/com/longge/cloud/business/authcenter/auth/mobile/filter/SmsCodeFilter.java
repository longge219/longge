package com.longge.cloud.business.authcenter.auth.mobile.filter;
import com.longge.cloud.business.authcenter.constant.SecurityConstant;
import com.longge.cloud.business.authcenter.exception.ValidateCodeException;
import com.longge.cloud.business.authcenter.service.CodeService;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.OncePerRequestFilter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
/**
 * @author: jianglong
 * @description: 验证码验证过滤器
 * @date: 2019-01-28
 * */
@Slf4j
public class SmsCodeFilter extends OncePerRequestFilter implements InitializingBean {

    /** 验证码校验失败处理器*/
    private AuthenticationFailureHandler authenticationFailureHandler;
	
    /**验证码服务*/
	@Setter
    private CodeService codeServiceImpl;

    /** 验证请求url与配置的url是否匹配的工具类*/
    private AntPathMatcher pathMatcher = new AntPathMatcher();
    
    /**路径集合*/
    private Set<String> urls = new HashSet<>();
    
    public SmsCodeFilter(CodeService codeServiceImpl, AuthenticationFailureHandler authenticationFailureHandler){
        this.codeServiceImpl = codeServiceImpl;
        this.authenticationFailureHandler = authenticationFailureHandler;
    }

    /**初始化要拦截的url配置信息*/
    @Override
    public void afterPropertiesSet() throws ServletException {
        super.afterPropertiesSet();
        urls.add(SecurityConstant.MOBILE_LOGINURL);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        boolean action = false;
        for (String url : urls) {
            if (pathMatcher.match(url, request.getRequestURI())) {
                action = true;
            }
        }
        if (action) {
            try {
                redisValidate(new ServletWebRequest(request));
            } catch (ValidateCodeException e) {
                authenticationFailureHandler.onAuthenticationFailure(request, response, e);
                e.printStackTrace();
                return;
            }
        }
        chain.doFilter(request, response);
    }

    /**验证码逻辑判断*/
    private void redisValidate(ServletWebRequest request) throws ServletRequestBindingException {
        String mobile = request.getParameter(SecurityConstant.MOBILE_PARAM);
        log.info(mobile);
        if (null == codeServiceImpl.getCode(mobile)){
            throw new ValidateCodeException("验证码已过期" + mobile);
        }
        String codeInRedis = codeServiceImpl.getCode(mobile).toString();
        //从请求中拿到验证码这个参数
        String codeInRequest = ServletRequestUtils.getStringParameter(request.getRequest(), SecurityConstant.MOBILE_VERIFYCODE);
        if (!codeInRedis.equals(codeInRequest)) {
            throw new ValidateCodeException("验证码不匹配");
        }
    }
}
