package com.longge.cloud.business.authcenter.service.impl;
import com.longge.cloud.business.authcenter.service.CodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import java.util.concurrent.TimeUnit;
/**
 * @author: jianglong 
 * @description:验证码接口实现
 * @date: 2019-01-25
 * */
@Service("codeServiceImpl")
public class CodeServiceImpl implements CodeService {

    @Autowired
    private RedisTemplate<Object, Object> cusRedisTemplate;

    /**
     * @description           保存验证码
     * @param mobile        手机号
     * @param code           验证码
     * @param expireTime  保存时间
     * @param unit            保存得单位时间
     */
    @Override
    public void saveCode(String mobile, String code, long expireTime, TimeUnit unit) {
        try {
            ValueOperations<Object, Object> operations = cusRedisTemplate.opsForValue();
            operations.set(mobile, code);
            cusRedisTemplate.expire(mobile, expireTime, unit);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @description 获取验证码
     * @param mobile 手机号
     * @return 验证码
     */
    @Override
    public Object getCode(String mobile) {
        Object result;
        ValueOperations<Object, Object> operations = cusRedisTemplate.opsForValue();
        result = operations.get(mobile);
        return result;
    }

    /**
     * @description 删除验证码
     * @param mobile
     */
    @Override
    public void removeCode(String mobile) {
        if(cusRedisTemplate.hasKey(mobile)){
        	cusRedisTemplate.delete(mobile);
        }
    }
    
}
