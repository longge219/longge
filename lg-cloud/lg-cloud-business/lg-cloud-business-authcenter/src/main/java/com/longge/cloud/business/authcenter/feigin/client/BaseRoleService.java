package com.longge.cloud.business.authcenter.feigin.client;
import com.longge.cloud.business.authcenter.constant.SecurityConstant;
import com.longge.cloud.business.authcenter.feigin.api.BaseRoleRemoteService;
import com.longge.cloud.business.authcenter.feigin.model.BaseRoleVo;
import com.longge.cloud.business.authcenter.response.ResponseCode;
import com.longge.cloud.business.authcenter.response.ResponseData;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.List;
/**
 * @author: jianglong
 * @description: 角色远程调用
 * @date: 2019-01-14
 */
@FeignClient(name = SecurityConstant.SERVICE_NAME, fallback = BaseRoleService.HystrixClientFallback.class)
public interface BaseRoleService extends BaseRoleRemoteService {
	@Component 
	class HystrixClientFallback implements BaseRoleService{
        @Override
        public ResponseData<List<BaseRoleVo>> findRoleByUserId(@RequestParam("userId") String userId) {
            return new ResponseData<>(ResponseCode.ERROR.getCode(), ResponseCode.ERROR.getMessage());
        }
    }
}
