package com.longge.cloud.business.common.basic.service;
import com.longge.cloud.business.common.basic.model.BaseUserRole;
import com.longge.plugins.mysql.service.BaseService;
import java.util.List;

public interface BaseUserRoleService  extends BaseService<BaseUserRole> {

    public void saveUserRole(List<BaseUserRole> baseUserRoleList);
}
