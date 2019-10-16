package com.longge.gather.http.user.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * @description 用户信息
 * @author jianglong
 * @create 2019-09-25
 **/
@Table(name = "xcnet_user_info")
@Data
public class XcnetUserInfo implements Serializable {
    @Id
    @Column(name = "usercode")
    private String userCode; //用户编号

    @Column(name = "cellphone")
    private String cellphone; //登录账户-手机号码

    @Column(name = "password")
    private String password; //密码

    @Column(name = "usertype")
    private byte userType; //用户类型(0:开发者用户，1:企业用户)

    @Column(name = "verifstatus")
    private byte verifStatus; //认证状态(0：未审核；1：审核中；2：审核失败；3：升级中；4：审核通过)

    @Column(name = "registerdate")
    private Date registerDate; //注册日期

    @Column(name = "verifysubmitdate")
    private Date verifySubmitDate; //认证提交时间

    @Column(name = "verifydate")
    private Date verifyDate; //认证通过日期

    @Column(name = "cancellationstatus")
    private byte cancellationStatus; //注销状态

    @Column(name = "cancellationdate")
    private Date cancellationDate; //注销日期

    @Column(name = "provincecode")
    private String provinceCode; //行政区编号

    @Column(name = "address")
    private String address; //街道

    @Column(name = "industrycode")
    private String industryCode; //行业编号

    @Column(name = "corporationname")
    private String corporationName; //企业名称

    @Column(name = "businesslicensefront")
    private String businesslicenseFront; //营业执照正面

    @Column(name = "businesslicenseback")
    private String businesslicenseBack; //营业执照背面

    @Column(name = "failurereason")
    private String failureReason; //审核失败原因
}
