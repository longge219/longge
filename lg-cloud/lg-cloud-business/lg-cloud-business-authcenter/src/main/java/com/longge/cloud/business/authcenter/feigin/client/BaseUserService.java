package com.longge.cloud.business.authcenter.feigin.client;
import com.longge.cloud.business.authcenter.constant.SecurityConstant;
import com.longge.cloud.business.authcenter.feigin.api.BaseUserRemoteService;
import com.longge.cloud.business.authcenter.feigin.model.BaseUserVo;
import com.longge.cloud.business.authcenter.response.ResponseCode;
import com.longge.cloud.business.authcenter.response.ResponseData;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;
/**
 * @author: jianglong
 * @description: 用户远程调用
 * @date: 2019-01-14
 */
@FeignClient(name = SecurityConstant.SERVICE_NAME, fallback = BaseUserService.HystrixClientFallback.class)
public interface BaseUserService extends BaseUserRemoteService {
	@Component
     class HystrixClientFallback implements BaseUserService{
        @Override
        public ResponseData<BaseUserVo> findUserByUserName(@RequestParam("userName") String userName) {
            return new ResponseData<>(ResponseCode.ERROR.getCode(),ResponseCode.ERROR.getMessage());
        }
        @Override
        public ResponseData<BaseUserVo> findUserByPhone(@RequestParam("phone") String phone) {
            return new ResponseData<>(ResponseCode.ERROR.getCode(),ResponseCode.ERROR.getMessage());
        }
    }
}
