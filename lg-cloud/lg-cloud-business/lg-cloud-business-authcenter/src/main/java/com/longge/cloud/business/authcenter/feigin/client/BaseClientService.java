package com.longge.cloud.business.authcenter.feigin.client;
import com.longge.cloud.business.authcenter.constant.SecurityConstant;
import com.longge.cloud.business.authcenter.feigin.api.BaseClientRemoteService;
import com.longge.cloud.business.authcenter.feigin.model.OauthClientDetailsVo;
import com.longge.cloud.business.authcenter.response.ResponseCode;
import com.longge.cloud.business.authcenter.response.ResponseData;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.List;
/**
 * @author: jianglong
 * @description: 客户端远程调用
 * @date: 2019-01-14
 */
@FeignClient(name = SecurityConstant.SERVICE_NAME, fallback = BaseClientService.HystrixClientFallback.class)
public interface BaseClientService extends BaseClientRemoteService {
	 @Component
     class HystrixClientFallback implements BaseClientService {
        @Override
        public ResponseData<List<OauthClientDetailsVo>> findClientByClientId(@RequestParam("clientId") String clientId){
            return new ResponseData<>(ResponseCode.ERROR.getCode(),ResponseCode.ERROR.getMessage());
        }
    }
}
