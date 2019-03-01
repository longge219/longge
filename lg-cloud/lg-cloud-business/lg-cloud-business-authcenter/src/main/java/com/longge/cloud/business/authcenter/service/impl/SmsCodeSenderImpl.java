package com.longge.cloud.business.authcenter.service.impl;
import com.longge.cloud.business.authcenter.service.SmsCodeSender;
import com.longge.cloud.business.authcenter.vo.ValidateCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
/**
 * @author: jianglong
 * @description:向手机发送短信验证码接口
 * @date: 2019-01-25
 * */
@Slf4j
@Service("smsCodeSenderImpl")
public class SmsCodeSenderImpl implements SmsCodeSender {
	
    /**
     * @description  发送验证码(调用第三方平台发送短信)
     * @param mobile 手机号
     * @param code    验证码
     */
    @Override
    public ValidateCode send(String mobile, String code) {
        log.info("向手机:"+mobile+"发送短信验证码:"+code);
        return new ValidateCode(code,60);
    }
}
