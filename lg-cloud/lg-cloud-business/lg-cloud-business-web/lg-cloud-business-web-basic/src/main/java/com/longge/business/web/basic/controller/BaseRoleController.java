package com.longge.business.web.basic.controller;
import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import com.longge.cloud.business.common.basic.model.BaseRole;
import com.longge.cloud.business.common.basic.model.BaseRoleModule;
import com.longge.cloud.business.common.basic.pojo.ResponseCode;
import com.longge.cloud.business.common.basic.pojo.request.BaseRoleRequest;
import com.longge.cloud.business.common.basic.service.BaseRoleModuleService;
import com.longge.cloud.business.common.basic.service.BaseRoleService;
import com.longge.cloud.business.common.pojo.ResponseData;
import com.longge.cloud.business.common.pojo.TableData;
import com.longge.cloud.business.common.utils.UUID;
import com.longge.cloud.business.plugins.mybatis.controller.CrudController;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import tk.mybatis.mapper.entity.Example;
import java.util.Date;
import java.util.List;


@RestController
public class BaseRoleController extends CrudController<BaseRole, BaseRoleRequest> {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Reference(version = "1.0")
    private BaseRoleService baseRoleService;

    @Reference(version = "1.0")
    private BaseRoleModuleService baseRoleModuleService;

    public ResponseData<List<BaseRole>> getRoleByUserId(@PathVariable("userId") String userId) {
        logger.debug("根据用户查询角色");
        List<BaseRole> list;
        try {
            list = baseRoleService.getRoleByUserId(userId);
        }catch (Exception e){
            logger.error("根据用户查询角色失败");
            e.printStackTrace();
            return new ResponseData<>(ResponseCode.ERROR.getCode(), ResponseCode.ERROR.getMessage());
        }
        return new ResponseData<>(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage(), list);
    }

    @GetMapping("/role")
    public ResponseData<List<BaseRole>> getAllRole() {
        logger.debug("获取所有角色");
        List<BaseRole> list;
        try {
            list = baseRoleService.selectAll();
        }catch (Exception e){
            logger.error("获取所有角色失败");
            e.printStackTrace();
            return new ResponseData<>(ResponseCode.ERROR.getCode(), ResponseCode.ERROR.getMessage());
        }
        return new ResponseData<>(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage(), list);
    }

    @PostMapping("/role/table")
    @Override
    protected ResponseData<TableData<BaseRole>> queryRecord(@RequestBody BaseRoleRequest query) {
        logger.debug("查询角色表格");
        Example example = new Example(BaseRole.class);
        Example.Criteria criteria = example.createCriteria();

        if(!StringUtils.isEmpty(query.getRoleCode())) {
            criteria.andLike("roleCode", "%" + query.getRoleCode() + "%");
        }
        if(!StringUtils.isEmpty(query.getRoleName())) {
            criteria.andLike("roleName", "%" + query.getRoleName() + "%");
        }

        PageInfo<BaseRole> pageInfo = baseRoleService.selectByExampleList(example, query.getPageNum(), query.getPageSize());

        return getTableData(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage(), pageInfo);
    }

    @PostMapping("/role")
    @Override
    protected ResponseData<BaseRole> addRecord(@RequestBody BaseRole record) {
        logger.debug("添加角色");
        try {
            record.setId(UUID.uuid32());
            record.setCreateDate(new Date());
            baseRoleService.insertSelective(record);
        } catch (Exception e) {
            logger.error("添加角色失败：" + e.getMessage());
            e.printStackTrace();
            return new ResponseData<>(ResponseCode.ERROR.getCode(), ResponseCode.ERROR.getMessage());
        }
        return new ResponseData<>(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage());
    }

    @DeleteMapping("/role")
    @Override
    protected ResponseData<BaseRole> deleteRecord(@RequestBody List<BaseRole> record) {
        logger.debug("删除角色");
        try {
            baseRoleService.deleteBatchByPrimaryKey(record);
        } catch (Exception e) {
            logger.error("删除角色失败：" + e.getMessage());
            e.printStackTrace();
            return new ResponseData<>(ResponseCode.ERROR.getCode(), ResponseCode.ERROR.getMessage());
        }
        return new ResponseData<>(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage());
    }

    @PutMapping("/role")
    @Override
    protected ResponseData<BaseRole> updateRecord(@RequestBody BaseRole record) {
        logger.debug("更新角色");
        try {
            record.setUpdateDate(new Date());
            baseRoleService.updateByPrimaryKeySelective(record);
        } catch (Exception e) {
            logger.error("更新角色失败：" + e.getMessage());
            e.printStackTrace();
            return new ResponseData<>(ResponseCode.ERROR.getCode(), ResponseCode.ERROR.getMessage());
        }
        return new ResponseData<>(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage());
    }

    @GetMapping("/role/validate/{roleCode}")
    public ResponseData<BaseRole> validateRoleCode(@PathVariable("roleCode") String roleCode) {
        logger.debug("校验角色编码是否存在");
        BaseRole baseRole = new BaseRole();
        baseRole.setRoleCode(roleCode);
        baseRole = baseRoleService.selectOne(baseRole);
        if(baseRole == null) {
            return new ResponseData<>(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage());
        }
        return new ResponseData<>(ResponseCode.ERROR.getCode(), ResponseCode.ERROR.getMessage());
    }

    @GetMapping("/role/auth/{roleId}")
    public ResponseData<List<BaseRoleModule>> getAuthModule(@PathVariable("roleId") String roleId) {
        logger.debug("查询角色已授权模块");
        List<BaseRoleModule> list;
        try {
            list = baseRoleModuleService.selectLeafRoleModule(roleId);
        } catch (Exception e) {
            logger.error("查询角色已授权模块失败：" + e.getMessage());
            e.printStackTrace();
            return new ResponseData<>(ResponseCode.ERROR.getCode(), ResponseCode.ERROR.getMessage());
        }
        return new ResponseData<>(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage(), list);
    }
}
