package com.longge.cloud.business.authcenter.feigin.api;
import com.longge.cloud.business.authcenter.feigin.model.BaseUserVo;
import com.longge.cloud.business.authcenter.response.ResponseData;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
/**
 * @author: jianglong
 * @description: 用户远程调用API
 * @date: 2019-01-14
 */
public interface BaseUserRemoteService {
    /**
     * @description 根据用户名查询用户
     * @param userName
     * @return ResponseData
     */
    @RequestMapping(value = "/user/findUserByUserName", method = RequestMethod.GET)
    ResponseData<BaseUserVo> findUserByUserName(@RequestParam("userName") String userName);

    /**
     * @description 根据电话号码查询用户
     * @param phone
     * @return ResponseData
     */
    @RequestMapping(value = "/user/findUserByPhone", method = RequestMethod.GET)
    ResponseData<BaseUserVo> findUserByPhone(@RequestParam("phone") String phone);
}
