package com.longge.cloud.business.authcenter.web;
import java.util.concurrent.TimeUnit;

import com.longge.cloud.business.authcenter.response.ResponseCode;
import com.longge.cloud.business.authcenter.response.ResponseData;
import com.longge.cloud.business.authcenter.service.CodeService;
import com.longge.cloud.business.authcenter.service.SmsCodeSender;
import com.longge.cloud.business.authcenter.utils.RandomUtils;
import com.longge.cloud.business.authcenter.utils.Validator;
import com.longge.cloud.business.authcenter.vo.ValidateCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
/**
 * @author:jianglong
 * @description: 向手机发送短信验证码
 * @date: 2019-01-25
 * */
@RestController
public class SmsController {
	
    @Autowired
    private CodeService codeServiceImpl;
    
    @Autowired
    private SmsCodeSender smsCodeSenderImpl;

    @RequestMapping(value="/code/sms",method= RequestMethod.GET)
    public ResponseData<ValidateCode> createSmsCode(@RequestParam("mobile") String mobile){
    	//验证手机号参数的合法性
    	if(Validator.isMobile(mobile)){
        	//生成随机验证码
            String smsCode = RandomUtils.getRandomNum(4);
            // 保存验证码
            codeServiceImpl.saveCode(mobile, smsCode, 5, TimeUnit.MINUTES);
            //模拟手机发送，此时调用短信服务商接口
            ValidateCode ValidateCode =  smsCodeSenderImpl.send(mobile,smsCode);
            return new ResponseData<>(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage(), ValidateCode);
    	}else{
    		 return new ResponseData<>(ResponseCode.PARA_ERROR.getCode(), ResponseCode.PARA_ERROR.getMessage());
    	}

    }
}
