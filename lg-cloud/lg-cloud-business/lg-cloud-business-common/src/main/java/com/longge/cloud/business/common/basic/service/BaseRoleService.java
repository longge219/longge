package com.longge.cloud.business.common.basic.service;


import com.longge.cloud.business.common.basic.model.BaseRole;
import com.longge.cloud.business.plugins.mybatis.service.BaseService;

import java.util.List;

public interface BaseRoleService extends BaseService<BaseRole> {

	public List<BaseRole> getRoleByUserId(String userId);
	
}
