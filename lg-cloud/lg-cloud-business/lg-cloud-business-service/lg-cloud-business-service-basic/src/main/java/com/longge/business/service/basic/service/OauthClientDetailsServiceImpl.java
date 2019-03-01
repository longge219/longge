package com.longge.business.service.basic.service;
import com.alibaba.dubbo.config.annotation.Service;
import com.longge.cloud.business.common.basic.model.OauthClientDetails;
import com.longge.cloud.business.common.basic.service.OauthClientDetailsService;
import com.longge.cloud.business.plugins.mybatis.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Component;


@Service(version = "1.0",timeout = 10000,interfaceClass = OauthClientDetailsService.class)
@Component
public class OauthClientDetailsServiceImpl extends BaseServiceImpl<OauthClientDetails> implements OauthClientDetailsService {
}
