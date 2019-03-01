package com.longge.business.service.basic.mapper.mapper;
import com.longge.cloud.business.common.basic.model.BaseRole;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@org.apache.ibatis.annotations.Mapper
public interface BaseRoleMapper extends Mapper<BaseRole> {
    List<BaseRole> getRoleByUserId(@Param("userId") String userId);
}