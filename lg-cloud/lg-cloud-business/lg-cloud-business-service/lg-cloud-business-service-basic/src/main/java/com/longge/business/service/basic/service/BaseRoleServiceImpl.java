package com.longge.business.service.basic.service;
import com.alibaba.dubbo.config.annotation.Service;
import com.longge.business.service.basic.mapper.mapper.BaseRoleMapper;
import com.longge.cloud.business.common.basic.model.BaseRole;
import com.longge.cloud.business.common.basic.service.BaseRoleService;
import com.longge.plugins.mysql.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Component;

import java.util.List;

@Service(version = "1.0",timeout = 10000,interfaceClass = BaseRoleService.class)
@Component
public class BaseRoleServiceImpl extends BaseServiceImpl<BaseRole> implements BaseRoleService {

    /**
     * 根据用户查询角色
     * @param userId
     * @return
     */
    public List<BaseRole> getRoleByUserId(String userId) {
        return ((BaseRoleMapper)mapper).getRoleByUserId(userId);
    }
}
