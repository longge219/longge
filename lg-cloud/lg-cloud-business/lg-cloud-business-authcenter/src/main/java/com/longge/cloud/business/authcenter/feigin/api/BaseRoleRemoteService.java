package com.longge.cloud.business.authcenter.feigin.api;
import com.longge.cloud.business.authcenter.feigin.model.BaseRoleVo;
import com.longge.cloud.business.authcenter.response.ResponseData;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.List;
/**
 * @author: jianglong
 * @description: 角色远程调用API
 * @date: 2019-01-14
 */
public interface BaseRoleRemoteService {
    /**
     * @description 根据userId查询角色
     * @param userId
     * @return ResponseData
     */
    @RequestMapping(value = "/role/findRoleByUserId", method = RequestMethod.GET)
    ResponseData<List<BaseRoleVo>> findRoleByUserId(@RequestParam("userId") String userId);
}
