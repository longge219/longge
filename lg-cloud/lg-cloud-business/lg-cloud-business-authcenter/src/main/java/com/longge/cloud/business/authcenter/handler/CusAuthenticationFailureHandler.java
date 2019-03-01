package com.longge.cloud.business.authcenter.handler;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;
/**
 * @author: jianglong
 * @description:认证失败业务处理
 * @date: 2019-01-28
 * */
@Component("cusAuthenticationFailureHandler")
public class CusAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler{
	
	@Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
    	Example1(request,response,exception);
	}
	
	private void Example1(HttpServletRequest request, HttpServletResponse response,AuthenticationException exception) throws IOException, ServletException
	{
 	   //例1：直接返回字符串
 	   response.setCharacterEncoding("UTF-8");
 	   response.setContentType("application/json");
 	   response.getWriter().println("{\"ok\":0,\"msg\":\""+exception.getLocalizedMessage()+"\"}");		
	}
}
