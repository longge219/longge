package com.longge.business.service.basic.mapper.mapper;
import com.longge.cloud.business.common.basic.model.BaseSystem;
import com.longge.cloud.business.common.basic.pojo.response.ModuleAndSystemResponse;
import tk.mybatis.mapper.common.Mapper;
import java.util.List;

@org.apache.ibatis.annotations.Mapper
public interface BaseSystemMapper extends Mapper<BaseSystem> {
    List<ModuleAndSystemResponse> selectModuleAndSystem();
}