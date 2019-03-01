package com.longge.business.service.basic.service;
import com.alibaba.dubbo.config.annotation.Service;
import com.longge.business.service.basic.mapper.mapper.BaseSystemMapper;
import com.longge.cloud.business.common.basic.model.BaseSystem;
import com.longge.cloud.business.common.basic.pojo.response.ModuleAndSystemResponse;
import com.longge.cloud.business.common.basic.service.BaseRoleService;
import com.longge.cloud.business.common.basic.service.BaseSystemService;
import com.longge.cloud.business.plugins.mybatis.service.impl.BaseServiceImpl;
import java.util.List;
import org.springframework.stereotype.Component;
@Service(version = "1.0",timeout = 10000,interfaceClass = BaseSystemService.class)
@Component
public class BaseSystemServiceImpl extends BaseServiceImpl<BaseSystem> implements BaseSystemService {
    public List<ModuleAndSystemResponse> selectModuleAndSystem() {
        return ((BaseSystemMapper)mapper).selectModuleAndSystem();
    }
}
