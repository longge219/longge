package com.longge.cloud.business.authcenter.vo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;
import java.time.LocalDateTime;
/**
 * @author: jianglong
 * @description:自定义错误码枚举对象
 * @date: 2019-01-14
 * */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ValidateCode implements Serializable {

	private static final long serialVersionUID = -7830266159201128737L;
	
	//验证码
	private String code;
	
	//过期时间
    private LocalDateTime expireTime;

    /**构造函数*/
    public ValidateCode(String code, int seconds) {
        this.code = code;
        this.expireTime = LocalDateTime.now().plusSeconds(seconds);
    }

    /**判断验证码是否过期*/
    public boolean isExpried() {
        return LocalDateTime.now().isAfter(expireTime);
    }
}
