package com.longge.cloud.business.authcenter.feigin.api;
import com.longge.cloud.business.authcenter.feigin.model.BaseModuleResourcesVo;
import com.longge.cloud.business.authcenter.response.ResponseData;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.List;
/**
 * @author: jianglong
 * @description: 资源功能模块远程调用API
 * @date: 2019-01-14
 */
public interface BaseModuleResourcesRemoteService {
    /**
     * @description 根据userId查询菜单
     * @param userId
     * @return ResponseData
     */
    @RequestMapping(value = "/module/findModuleResourcesByUserId", method = RequestMethod.GET)
    ResponseData<List<BaseModuleResourcesVo>> findModuleResourcesByUserId(@RequestParam("userId") String userId);
}
