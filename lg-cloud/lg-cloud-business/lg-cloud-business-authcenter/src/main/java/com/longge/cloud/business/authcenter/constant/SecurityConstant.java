package com.longge.cloud.business.authcenter.constant;
/**
 * @author: jianglong
 * @description: 权限认证服务常量
 * @date: 2018-12-25
 * */
public class SecurityConstant {
	
	//feigin请求服务注册名
    public static final String SERVICE_NAME = "authweb";
    
    //手机验证码授权请求路径
    public static final String MOBILE_LOGINURL = "/authentication/mobile";
    
    //手机验证码授权传入参数名mobile手机号
    public static final String MOBILE_PARAM= "mobile";
    
    //手机验证码授权传入参数名verifyCode验证码
    public static final String MOBILE_VERIFYCODE = "verifyCode";
    
    //手机登录权限
    public static final String MOBILE_AUTHORITY = "common";
    
}
