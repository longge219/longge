package com.longge.cloud.business.common.basic.service;
import com.longge.cloud.business.common.basic.model.BaseUser;
import com.longge.cloud.business.plugins.mybatis.service.BaseService;

import java.util.List;


public interface BaseUserService  extends BaseService<BaseUser> {

	public void resetPassword(List<BaseUser> record, String newPassword);

	public void deleteBatch(List<BaseUser> record);

}
