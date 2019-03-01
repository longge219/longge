package com.longge.cloud.business.authcenter.auth.mobile.userservice;
import java.util.ArrayList;
import java.util.List;

import com.longge.cloud.business.authcenter.auth.base.BaseUserDetail;
import com.longge.cloud.business.authcenter.constant.SecurityConstant;
import com.longge.cloud.business.authcenter.feigin.client.BaseUserService;
import com.longge.cloud.business.authcenter.feigin.model.BaseUserVo;
import com.longge.cloud.business.authcenter.response.ResponseCode;
import com.longge.cloud.business.authcenter.response.ResponseData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
/**
 * @description: 手机登录验证
 * @author:  jianglong
 * @date: 2018-12-13
 * */
@Service("mobileUserDetailService")
public class MobileUserDetailService implements UserDetailsService{

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    
    //FeiginClient用户查询接口
    @Autowired
    protected BaseUserService baseUserService;

    /**返回UserDetails的实现user不为空，则验证通过*/
    @Override
    public UserDetails loadUserByUsername(String mobile) throws UsernameNotFoundException {
        // 手机验证码调用FeignClient根据电话号码查询用户
        ResponseData<BaseUserVo> baseUserResponseData = baseUserService.findUserByPhone(mobile);
        if(baseUserResponseData.getData() == null || !ResponseCode.SUCCESS.getCode().equals(baseUserResponseData.getCode())){
            logger.error("找不到该用户，手机号码：" + mobile);
            throw new UsernameNotFoundException("找不到该用户，手机号码：" + mobile);
        }
        BaseUserVo baseUser = baseUserResponseData.getData();
        // 返回带有用户权限信息的User
        User user =  new User(baseUser.getUserName(),baseUser.getPassword(), baseUser.getActive() == 1 ? true : false, true, true, true, convertToAuthorities());
        return new BaseUserDetail(baseUser, user);
    }    
    
    /**将用户的权限存储到redis中*/
    private List<GrantedAuthority> convertToAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        //mobile登录固定普通角色
        GrantedAuthority authority = new SimpleGrantedAuthority(SecurityConstant.MOBILE_AUTHORITY);
        authorities.add(authority);
        return authorities;
    }
}
