package com.longge.cloud.business.authcenter.feigin.api;
import com.longge.cloud.business.authcenter.feigin.model.OauthClientDetailsVo;
import com.longge.cloud.business.authcenter.response.ResponseData;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.List;
/**
 * @author: jianglong
 * @description: 客户端远程调用API
 * @date: 2019-01-14
 */
public interface BaseClientRemoteService {
    /**
     * @description 根据客户端ID查询客户端信息
     * @param clientId
     * @return ResponseData
     */
    @RequestMapping(value = "/client/findClientByClientId", method = RequestMethod.GET)
    ResponseData<List<OauthClientDetailsVo>> findClientByClientId(@RequestParam("clientId") String clientId);

}
