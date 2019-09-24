package com.longge.cloud.business.common.basic.service;
import com.longge.cloud.business.common.basic.model.BaseModuleResources;
import com.longge.plugins.mysql.service.BaseService;
import java.util.List;

/**
 * @author jianglong
 * @descrition 资源管理服务接口
 * @date 2019-02-26
 * */
public interface BaseModuleResourceService extends BaseService<BaseModuleResources> {

	/**根据用户查询菜单*/
	public List<BaseModuleResources> getMenusByUserId(String userId);

	/**根据系统ID查询菜单树*/
	public List<BaseModuleResources> getModuleTree(String id, String systemId);
    
}
