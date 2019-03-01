package com.longge.cloud.business.authcenter.feigin.client;
import com.longge.cloud.business.authcenter.constant.SecurityConstant;
import com.longge.cloud.business.authcenter.feigin.api.BaseModuleResourcesRemoteService;
import com.longge.cloud.business.authcenter.feigin.model.BaseModuleResourcesVo;
import com.longge.cloud.business.authcenter.response.ResponseCode;
import com.longge.cloud.business.authcenter.response.ResponseData;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.List;
/**
 * @author: jianglong
 * @description: 资源功能模块远程调用
 * @date: 2019-01-14
 */
@FeignClient(name = SecurityConstant.SERVICE_NAME, fallback = BaseModuleResourceService.HystrixClientFallback.class)
public interface BaseModuleResourceService extends BaseModuleResourcesRemoteService {
	@Component
     class HystrixClientFallback implements BaseModuleResourceService{
        @Override
        public ResponseData<List<BaseModuleResourcesVo>> findModuleResourcesByUserId(@RequestParam("userId") String userId) {
            return new ResponseData<>(ResponseCode.ERROR.getCode(),ResponseCode.ERROR.getMessage());
        }
    }
}
