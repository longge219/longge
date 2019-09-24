package com.longge.cloud.business.common.basic.service;
import com.longge.cloud.business.common.basic.model.BaseSystem;
import com.longge.cloud.business.common.basic.pojo.response.ModuleAndSystemResponse;
import com.longge.plugins.mysql.service.BaseService;
import java.util.List;

public interface BaseSystemService extends BaseService<BaseSystem> {

    public List<ModuleAndSystemResponse> selectModuleAndSystem();
	
}
