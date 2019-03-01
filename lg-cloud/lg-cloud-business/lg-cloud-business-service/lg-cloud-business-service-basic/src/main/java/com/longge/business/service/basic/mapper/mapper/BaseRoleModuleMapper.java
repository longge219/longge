package com.longge.business.service.basic.mapper.mapper;
import com.longge.cloud.business.common.basic.model.BaseRoleModule;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@org.apache.ibatis.annotations.Mapper
public interface BaseRoleModuleMapper extends Mapper<BaseRoleModule> {
    List<BaseRoleModule> selectLeafRoleModule(String roleId);
}