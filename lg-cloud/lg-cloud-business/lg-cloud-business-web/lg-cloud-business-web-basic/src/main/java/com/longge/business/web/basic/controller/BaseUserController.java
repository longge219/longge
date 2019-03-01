package com.longge.business.web.basic.controller;
import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import com.longge.cloud.business.common.basic.model.BaseUser;
import com.longge.cloud.business.common.basic.model.BaseUserRole;
import com.longge.cloud.business.common.basic.pojo.ResponseCode;
import com.longge.cloud.business.common.basic.pojo.request.BaseUserRequest;
import com.longge.cloud.business.common.basic.service.BaseUserRoleService;
import com.longge.cloud.business.common.basic.service.BaseUserService;
import com.longge.cloud.business.common.pojo.ResponseData;
import com.longge.cloud.business.common.pojo.TableData;
import com.longge.cloud.business.common.utils.UUID;
import com.longge.cloud.business.plugins.mybatis.controller.CrudController;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.apache.commons.lang.StringUtils;
import tk.mybatis.mapper.entity.Example;
import java.util.Date;
import java.util.List;

/**
 * Created by fp295 on 2018/4/9.
 */
@RestController
public class BaseUserController extends CrudController<BaseUser, BaseUserRequest> {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Reference(version = "1.0")
    private BaseUserService baseUserService;

    @Reference(version = "1.0")
    private BaseUserRoleService baseUserRoleService;

    @ApiOperation(value = "根据用户名查找用户", notes = "根据用户名查找用户", httpMethod = "GET")
    @ApiImplicitParam(name = "userName", value = "用户名", required = true, dataType = "String", paramType = "query")
    public ResponseData<BaseUser> getUserByUserName(@PathVariable("userName") String userName) {
        logger.debug("根据用户名查询用户");
        if(StringUtils.isEmpty(userName)){
            return new ResponseData<>(ResponseCode.ERROR.getCode(), ResponseCode.ERROR.getMessage());
        }
        BaseUser baseUser = new BaseUser();
        baseUser.setUserName(userName);
        baseUser = baseUserService.selectOne(baseUser);
        return new ResponseData<>(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage(), baseUser);
    }

    /**
     * 根据电话号码查询用户信息
     * @param phone
     * @return
     */
    public ResponseData<BaseUser> getUserByPhone(@PathVariable("phone") String phone) {
        logger.debug("根据电话号码查询用户");
        if(StringUtils.isEmpty(phone)){
            return new ResponseData<>(ResponseCode.ERROR.getCode(), ResponseCode.ERROR.getMessage());
        }
        BaseUser baseUser = new BaseUser();
        baseUser.setPhone(phone);
        baseUser = baseUserService.selectOne(baseUser);
        return new ResponseData<>(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage(), baseUser);
    }

    @PostMapping("/user/table")
    @Override
    protected ResponseData<TableData<BaseUser>> queryRecord(@RequestBody BaseUserRequest query) {
        logger.debug("查询用户");
        Example example = new Example(BaseUser.class);
        Example.Criteria criteria = example.createCriteria();

        if(!StringUtils.isEmpty(query.getUserName())) {
            criteria.andLike("userName", "%" + query.getUserName() + "%");
        }
        if(!StringUtils.isEmpty(query.getPhone())) {
            criteria.andLike("phone", "%" + query.getPhone() + "%");
        }

        PageInfo<BaseUser> pageInfo = baseUserService.selectByExampleList(example, query.getPageNum(), query.getPageSize());

        return getTableData(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage(), pageInfo);
    }

    @PostMapping("/user")
    @Override
    protected ResponseData<BaseUser> addRecord(@RequestBody BaseUser record) {
        logger.debug("添加用户");
        try {
            logger.debug("用户密码加密");
            record.setId(UUID.uuid32());
            record.setCreateDate(new Date());
            record.setPassword(new BCryptPasswordEncoder(6).encode(record.getPassword()));
            baseUserService.insertSelective(record);
        } catch (Exception e) {
            logger.error("添加用户失败：" + e.getMessage());
            e.printStackTrace();
            return new ResponseData<>(ResponseCode.ERROR.getCode(), ResponseCode.ERROR.getMessage());
        }
        return new ResponseData<>(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage());
    }

    @DeleteMapping("/user")
    @Override
    protected ResponseData<BaseUser> deleteRecord(@RequestBody List<BaseUser> record) {
        logger.debug("删除用户");
        try {
            baseUserService.deleteBatch(record);
        } catch (Exception e) {
            logger.error("删除用户失败：" + e.getMessage());
            e.printStackTrace();
            return new ResponseData<>(ResponseCode.ERROR.getCode(), ResponseCode.ERROR.getMessage());
        }
        return new ResponseData<>(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage());
    }

    @PutMapping("/user")
    @Override
    protected ResponseData<BaseUser> updateRecord(@RequestBody BaseUser record) {
        logger.debug("更新用户");
        try {
            record.setPassword(null);
            record.setUpdateDate(new Date());
            baseUserService.updateByPrimaryKeySelective(record);
        } catch (Exception e) {
            logger.error("更新用户失败：" + e.getMessage());
            e.printStackTrace();
            return new ResponseData<>(ResponseCode.ERROR.getCode(), ResponseCode.ERROR.getMessage());
        }
        return new ResponseData<>(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage());
    }

    /**
     * 重置密码
     * @param record
     * @return
     */
    @PostMapping("/user/password/{newPassword}")
    public ResponseData<BaseUser> resetPassword(@RequestBody List<BaseUser> record, @PathVariable("newPassword") String newPassword) {
        logger.debug("重置密码");
        try {
            baseUserService.resetPassword(record, newPassword);
        } catch (Exception e) {
            logger.error("重置密码用户失败：" + e.getMessage());
            e.printStackTrace();
            return new ResponseData<>(ResponseCode.ERROR.getCode(), ResponseCode.ERROR.getMessage());
        }
        return new ResponseData<>(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage());
    }

    @GetMapping("/user/role/{userId}")
    public ResponseData<List<BaseUserRole>> saveUserRole(@PathVariable("userId") String userId) {
        logger.debug("获取用户授权角色");
        List<BaseUserRole> list;
        try {
            Example example = new Example(BaseUserRole.class);
            example.createCriteria().andEqualTo("userId", userId);
            list = baseUserRoleService.selectByExample(example);
        } catch (Exception e) {
            logger.error("获取用户授权角色失败" + e.getMessage());
            e.printStackTrace();
            return new ResponseData<>(ResponseCode.ERROR.getCode(), ResponseCode.ERROR.getMessage());
        }
        return new ResponseData<>(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage(), list);
    }

    @PostMapping("/user/role")
    public ResponseData<BaseUser> saveUserRole(@RequestBody List<BaseUserRole> baseUserRoleList) {
        logger.debug("保存用户授权角色");
        try {
            baseUserRoleService.saveUserRole(baseUserRoleList);
        } catch (Exception e) {
            logger.error("保存用户授权角色失败" + e.getMessage());
            e.printStackTrace();
            return new ResponseData<>(ResponseCode.ERROR.getCode(), ResponseCode.ERROR.getMessage());
        }
        return new ResponseData<>(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage());
    }

    @GetMapping("/user/validate/{userName}")
    public ResponseData<BaseUser> validateUserName(@PathVariable("userName") String userName) {
        logger.debug("校验用户名是否存在");
        BaseUser baseUser = new BaseUser();
        baseUser.setUserName(userName);
        baseUser = baseUserService.selectOne(baseUser);
        if(baseUser == null) {
            return new ResponseData<>(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage());
        }
        return new ResponseData<>(ResponseCode.ERROR.getCode(), ResponseCode.ERROR.getMessage());
    }

    @GetMapping("/user/validate/phone/{phone}")
    public ResponseData<BaseUser> validatePhone(@PathVariable("phone") String phone) {
        logger.debug("校验手机号码是否存在");
        BaseUser baseUser = new BaseUser();
        baseUser.setPhone(phone);
        baseUser = baseUserService.selectOne(baseUser);
        if(baseUser == null) {
            return new ResponseData<>(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage());
        }
        return new ResponseData<>(ResponseCode.ERROR.getCode(), ResponseCode.ERROR.getMessage());
    }
}
