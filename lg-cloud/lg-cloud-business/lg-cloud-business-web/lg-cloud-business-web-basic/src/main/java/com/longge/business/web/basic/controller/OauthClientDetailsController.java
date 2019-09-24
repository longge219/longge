package com.longge.business.web.basic.controller;
import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import com.longge.cloud.business.common.basic.model.OauthClientDetails;
import com.longge.cloud.business.common.basic.pojo.ResponseCode;
import com.longge.cloud.business.common.basic.pojo.request.OauthClientDetailsRequest;
import com.longge.cloud.business.common.basic.service.OauthClientDetailsService;
import com.longge.cloud.business.common.pojo.ResponseData;
import com.longge.cloud.business.common.pojo.TableData;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@RestController
public class OauthClientDetailsController extends CrudController<OauthClientDetails, OauthClientDetailsRequest>{

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Reference(version = "1.0")
    private OauthClientDetailsService oauthClientDetailsService;

    @PostMapping("/client/table")
    @Override
    protected ResponseData<TableData<OauthClientDetails>> queryRecord(@RequestBody OauthClientDetailsRequest query) {
        logger.debug("查询客户端表格");
        Example example = new Example(OauthClientDetails.class);
        Example.Criteria criteria = example.createCriteria();

        if (!StringUtils.isEmpty(query.getName())) {
            criteria.andLike("name", "%" + query.getName() + "%");
        }
        PageInfo<OauthClientDetails> pageInfo = oauthClientDetailsService.selectByExampleList(example, query.getPageNum(), query.getPageSize());

        return getTableData(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage(), pageInfo);
    }

    @PostMapping("/client")
    @Override
    protected ResponseData<OauthClientDetails> addRecord(@RequestBody OauthClientDetails record) {
        logger.debug("添加客户端应用");
        try {
            oauthClientDetailsService.insertSelective(record);
        } catch (Exception e) {
            logger.error("添加客户端应用失败：" + e.getMessage());
            e.printStackTrace();
            return new ResponseData<>(ResponseCode.ERROR.getCode(), ResponseCode.ERROR.getMessage());
        }
        return new ResponseData<>(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage());
    }

    @DeleteMapping("/client")
    @Override
    protected ResponseData<OauthClientDetails> deleteRecord(@RequestBody List<OauthClientDetails> record) {
        logger.debug("删除客户端应用");
        try {
            oauthClientDetailsService.deleteBatchByPrimaryKey(record);
        } catch (Exception e) {
            logger.error("删除客户端应用失败：" + e.getMessage());
            e.printStackTrace();
            return new ResponseData<>(ResponseCode.ERROR.getCode(), ResponseCode.ERROR.getMessage());
        }
        return new ResponseData<>(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage());
    }

    @PutMapping("/client")
    @Override
    protected ResponseData<OauthClientDetails> updateRecord(@RequestBody OauthClientDetails record) {
        logger.debug("更新客户端应用");
        try {
            oauthClientDetailsService.updateByPrimaryKeySelective(record);
        } catch (Exception e) {
            logger.error("更新客户端应用失败：" + e.getMessage());
            e.printStackTrace();
            return new ResponseData<>(ResponseCode.ERROR.getCode(), ResponseCode.ERROR.getMessage());
        }
        return new ResponseData<>(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage());
    }

    @GetMapping("/client/validate/{clientId}")
    protected ResponseData<OauthClientDetails> validateClientId(@PathVariable("clientId") String clientId) {
        logger.debug("校验应用id是否存在");
        OauthClientDetails oauthClientDetails = new OauthClientDetails();
        oauthClientDetails.setClientId(clientId);
        oauthClientDetails = oauthClientDetailsService.selectOne(oauthClientDetails);
        if(oauthClientDetails == null) {
            return new ResponseData<>(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage());
        }
        return new ResponseData<>(ResponseCode.ERROR.getCode(), ResponseCode.ERROR.getMessage());
    }

    public ResponseData<List<OauthClientDetails>> getAllClient() {
        logger.debug("获取所有客户端应用");
        List<OauthClientDetails> list;
        try {
            list = oauthClientDetailsService.selectAll();
        } catch (Exception e) {
            logger.error("获取所有客户端应用失败：" + e.getMessage());
            e.printStackTrace();
            return new ResponseData<>(ResponseCode.ERROR.getCode(), ResponseCode.ERROR.getMessage());
        }
        return new ResponseData<>(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage(), list);
    }
}
