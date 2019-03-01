package com.longge.cloud.business.authcenter.service;
import java.util.concurrent.TimeUnit;
/**
 * @author: jianglong
 * @description: 验证码接口
 * @date: 2019-01-25
 * */
public interface CodeService {

    /**
     * @description  保存验证码
     * @param mobile        手机号
     * @param code           验证码
     * @param expireTime  保存时间
     * @param unit            保存得单位时间
     */
    void saveCode(String mobile, String code, long expireTime, TimeUnit unit);

    /**
     * @description 获取验证码
     * @param mobile 手机号
     * @return 验证码
     */
    Object getCode(String mobile);

    /**
     * @description 删除验证码
     * @param mobile
     */
    void removeCode(String mobile);
}
