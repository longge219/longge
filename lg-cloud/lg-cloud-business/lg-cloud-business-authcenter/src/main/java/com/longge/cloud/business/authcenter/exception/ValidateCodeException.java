package com.longge.cloud.business.authcenter.exception;
import org.springframework.security.core.AuthenticationException;
/**
 * @author: jianglong
 * @description: 验证码异常类
 * @date: 2019-01-25
 * */
public class ValidateCodeException extends AuthenticationException {

	private static final long serialVersionUID = -8605936497354415280L;

	public ValidateCodeException(String msg) {
               super("校验失败");
    }
}
