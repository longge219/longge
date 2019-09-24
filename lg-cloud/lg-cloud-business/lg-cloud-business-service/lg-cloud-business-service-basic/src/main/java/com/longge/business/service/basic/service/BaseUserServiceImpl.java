package com.longge.business.service.basic.service;
import com.alibaba.dubbo.config.annotation.Service;
import com.longge.cloud.business.common.basic.model.BaseUser;
import com.longge.cloud.business.common.basic.model.BaseUserRole;
import com.longge.cloud.business.common.basic.service.BaseUserService;
import com.longge.plugins.mysql.service.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;
import java.util.Date;
import java.util.List;

@Service(version = "1.0",timeout = 10000,interfaceClass = BaseUserService.class)
@Component
public class BaseUserServiceImpl extends BaseServiceImpl<BaseUser> implements BaseUserService {

    @Autowired
    private BaseUserRoleServiceImpl baseUserRoleService;


    /**
     * 批量重置密码
     * @param record
     * @param newPassword
     */
    @Transactional
    public void resetPassword(List<BaseUser> record, String newPassword) {
        record.forEach(e -> {
            BaseUser baseUser = new BaseUser();
            baseUser.setId(e.getId());
           // baseUser.setPassword(new BCryptPasswordEncoder(6).encode(newPassword));
            baseUser.setUpdateDate(new Date());
            updateByPrimaryKeySelective(baseUser);
        });
    }

    /**
     * 删除用户
     * @param record
     */
    @Transactional
    public void deleteBatch(List<BaseUser> record) {
        record.forEach(e -> {
            Example example = new Example(BaseUserRole.class);
            example.createCriteria().andEqualTo("userId", e.getId());
            baseUserRoleService.deleteByExample(example);
            deleteByPrimaryKey(e);
        });
    }
}
