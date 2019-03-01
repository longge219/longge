package com.longge.business.web.basic.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import com.longge.cloud.business.common.basic.model.BaseSystem;
import com.longge.cloud.business.common.basic.pojo.ResponseCode;
import com.longge.cloud.business.common.basic.pojo.request.BaseSystemRequest;
import com.longge.cloud.business.common.basic.pojo.response.ModuleAndSystemResponse;
import com.longge.cloud.business.common.basic.service.BaseSystemService;
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

/**
 * Created by fp295 on 2018/5/13.
 */
@RestController
public class BaseSystemController extends CrudController<BaseSystem, BaseSystemRequest> {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Reference(version = "1.0")
    private BaseSystemService baseSystemService;

    @PostMapping("/system/table")
    protected ResponseData<TableData<BaseSystem>> queryRecord(@RequestBody BaseSystemRequest query) {
        logger.debug("查询系统表格");
        Example example = new Example(BaseSystem.class);
        Example.Criteria criteria = example.createCriteria();

        if(!StringUtils.isEmpty(query.getProjectName())) {
            criteria.andLike("projectName", "%" + query.getProjectName() + "%");
        }
        if(!StringUtils.isEmpty(query.getSystemName())) {
            criteria.andLike("systemName", "%" + query.getSystemName() + "%");
        }

        PageInfo<BaseSystem> pageInfo = baseSystemService.selectByExampleList(example, query.getPageNum(), query.getPageSize());

        return getTableData(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage(), pageInfo);
    }

    @PostMapping("/system")
    protected ResponseData<BaseSystem> addRecord(@RequestBody BaseSystem record) {
        logger.debug("添加系统");
        try {
            record.setId(UUID.uuid32());
            record.setCreateDate(new Date());
            baseSystemService.insertSelective(record);
        } catch (Exception e) {
            logger.error("添加系统失败：" + e.getMessage());
            e.printStackTrace();
            return new ResponseData<>(ResponseCode.ERROR.getCode(), ResponseCode.ERROR.getMessage());
        }
        return new ResponseData<>(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage());
    }

    @DeleteMapping("/system")
    protected ResponseData<BaseSystem> deleteRecord(@RequestBody List<BaseSystem> record) {
        logger.debug("删除系统");
        try {
            baseSystemService.deleteBatchByPrimaryKey(record);
        } catch (Exception e) {
            logger.error("删除系统失败：" + e.getMessage());
            e.printStackTrace();
            return new ResponseData<>(ResponseCode.ERROR.getCode(), ResponseCode.ERROR.getMessage());
        }
        return new ResponseData<>(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage());
    }

    @PutMapping("/system")
    protected ResponseData<BaseSystem> updateRecord(@RequestBody BaseSystem record) {
        logger.debug("更新系统");
        try {
            record.setUpdateDate(new Date());
            baseSystemService.updateByPrimaryKeySelective(record);
        } catch (Exception e) {
            logger.error("更新系统失败：" + e.getMessage());
            e.printStackTrace();
            return new ResponseData<>(ResponseCode.ERROR.getCode(), ResponseCode.ERROR.getMessage());
        }
        return new ResponseData<>(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage());
    }

    @GetMapping("/system/validate/{projectName}")
    public ResponseData<BaseSystem> validateRoleCode(@PathVariable("projectName") String projectName) {
        logger.debug("校验系统项目名是否存在");
        BaseSystem baseSystem = new BaseSystem();
        baseSystem.setProjectName(projectName);
        baseSystem = baseSystemService.selectOne(baseSystem);
        if(baseSystem == null) {
            return new ResponseData<>(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage());
        }
        return new ResponseData<>(ResponseCode.ERROR.getCode(), ResponseCode.ERROR.getMessage());
    }

    @GetMapping("/system")
    public ResponseData<List<BaseSystem>> getSystem() {
        logger.debug("查询所有系统");
        List<BaseSystem> list;
        try {
            list = baseSystemService.selectAll();
        } catch (Exception e) {
            logger.error("查询所有系统失败：" + e.getMessage());
            e.printStackTrace();
            return new ResponseData<>(ResponseCode.ERROR.getCode(), ResponseCode.ERROR.getMessage());
        }
        return new ResponseData<>(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage(), list);
    }

    @GetMapping("/system/module")
    public ResponseData<List<ModuleAndSystemResponse>> getModuleAndSystem() {
        logger.debug("查询系统及模块树");
        List<ModuleAndSystemResponse> list;
        try {
            list = baseSystemService.selectModuleAndSystem();
        } catch (Exception e) {
            logger.error("查询系统及模块树失败：" + e.getMessage());
            e.printStackTrace();
            return new ResponseData<>(ResponseCode.ERROR.getCode(), ResponseCode.ERROR.getMessage());
        }
        return new ResponseData<>(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage(), list);
    }

}
