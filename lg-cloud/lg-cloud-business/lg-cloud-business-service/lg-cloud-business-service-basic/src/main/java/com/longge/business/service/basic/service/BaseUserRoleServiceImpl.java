package com.longge.business.service.basic.service;
import com.alibaba.dubbo.config.annotation.Service;
import com.longge.cloud.business.common.basic.model.BaseUserRole;
import com.longge.cloud.business.common.basic.service.BaseUserRoleService;
import com.longge.cloud.business.common.utils.UUID;
import com.longge.plugins.mysql.service.impl.BaseServiceImpl;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service(version = "1.0",timeout = 10000,interfaceClass = BaseUserRoleService.class)
@Component
public class BaseUserRoleServiceImpl extends BaseServiceImpl<BaseUserRole> implements BaseUserRoleService {

    /**
     * 保存用户角色
     * @param baseUserRoleList
     */
    @Transactional
    public void saveUserRole(List<BaseUserRole> baseUserRoleList) {
        if (baseUserRoleList.size() > 0 && !StringUtils.isEmpty(baseUserRoleList.get(0).getRoleId())) {
            BaseUserRole userRole = new BaseUserRole();
            userRole.setUserId(baseUserRoleList.get(0).getUserId());
            mapper.delete(userRole);
            baseUserRoleList.forEach(it -> {
                it.setId(UUID.uuid32());
                insertSelective(it);
            });
        }
    }
}
