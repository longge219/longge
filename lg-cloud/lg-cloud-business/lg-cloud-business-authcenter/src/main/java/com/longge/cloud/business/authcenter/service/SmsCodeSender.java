package com.longge.cloud.business.authcenter.service;
import com.longge.cloud.business.authcenter.vo.ValidateCode;

/**
 * @author:jianglong
 * @description:  向手机发送短信验证码接口
 * @date: 2019-01-25
 * */
public interface SmsCodeSender {

    /**
     * @description  发送验证码
     * @param mobile        手机号
     * @param code           验证码
     */
	ValidateCode send(String mobile, String code);
}
