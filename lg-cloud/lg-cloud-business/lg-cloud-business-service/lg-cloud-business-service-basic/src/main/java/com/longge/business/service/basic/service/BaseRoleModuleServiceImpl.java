package com.longge.business.service.basic.service;
import com.alibaba.dubbo.config.annotation.Service;
import com.longge.business.service.basic.mapper.mapper.BaseRoleModuleMapper;
import com.longge.cloud.business.common.basic.model.BaseRoleModule;
import com.longge.cloud.business.common.basic.service.BaseRoleModuleService;
import com.longge.cloud.business.common.utils.UUID;
import com.longge.cloud.business.plugins.mybatis.service.impl.BaseServiceImpl;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;


@Service(version = "1.0",timeout = 10000,interfaceClass = BaseRoleModuleService.class)
@Component
public class BaseRoleModuleServiceImpl extends BaseServiceImpl<BaseRoleModule> implements BaseRoleModuleService {

    @Transactional
    public void saveRoleModule(List<BaseRoleModule> roleModule) {
        if (roleModule.size() > 0 && !StringUtils.isEmpty(roleModule.get(0).getRoleId())) {
            BaseRoleModule module = new BaseRoleModule();
            module.setRoleId(roleModule.get(0).getRoleId());
            mapper.delete(module);
            roleModule.forEach(it -> {
                it.setId(UUID.uuid32());
                mapper.insertSelective(it);
            });
        }
    }

    // 查询关联角色的叶子模块
    public List<BaseRoleModule> selectLeafRoleModule(String roleId) {
        return ((BaseRoleModuleMapper)mapper).selectLeafRoleModule(roleId);
    }
}
