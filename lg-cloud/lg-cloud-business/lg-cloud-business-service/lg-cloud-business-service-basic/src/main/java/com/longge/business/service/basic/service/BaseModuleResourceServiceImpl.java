package com.longge.business.service.basic.service;
import com.longge.business.service.basic.mapper.mapper.BaseModuleResourcesMapper;
import com.longge.cloud.business.common.basic.model.BaseModuleResources;
import com.longge.cloud.business.common.basic.service.BaseModuleResourceService;
import com.longge.cloud.business.plugins.mybatis.service.impl.BaseServiceImpl;
import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.stereotype.Component;

import java.util.List;
/**
 * @author jianglong
 * @descrition 资源管理服务接口实现类
 * @date 2019-02-26
 * */
@Service(version = "1.0",timeout = 10000,interfaceClass = BaseModuleResourceService.class)
@Component
public class BaseModuleResourceServiceImpl extends BaseServiceImpl<BaseModuleResources> implements BaseModuleResourceService{


    /**
     * 根据用户查询菜单
     * @param userId 用户ID
     * @return BaseModuleResources
     */
    public List<BaseModuleResources> getMenusByUserId(String userId) {
        return ((BaseModuleResourcesMapper)mapper).getMenusByUserId(userId);
    }

    /**
     * 根据系统ID查询菜单树
     * @param id
     * @param systemId 系统ID
     * @return BaseModuleResources
     */
    public List<BaseModuleResources> getModuleTree(String id, String systemId) {
        return ((BaseModuleResourcesMapper)mapper).selectModuleTree(id, systemId);
    }
}
