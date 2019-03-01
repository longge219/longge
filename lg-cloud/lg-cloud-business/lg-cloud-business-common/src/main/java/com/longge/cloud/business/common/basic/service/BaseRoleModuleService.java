package com.longge.cloud.business.common.basic.service;
import com.longge.cloud.business.common.basic.model.BaseRoleModule;
import com.longge.cloud.business.plugins.mybatis.service.BaseService;

import java.util.List;

public interface BaseRoleModuleService extends BaseService<BaseRoleModule> {

    public void saveRoleModule(List<BaseRoleModule> roleModule);

    public List<BaseRoleModule> selectLeafRoleModule(String roleId);
}
