package com.longge.gather.http.project.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @description 项目信息
 * @author jianglong
 * @create 2019-09-25
 **/
@Table(name = "xcnet_project_info")
@Data
public class XcnetProjectInfo implements Serializable {
    @Id
    @Column(name = "projectcode")
    private String projectCode; //项目编号

    @Column(name = "projectname")
    private String projectName; //项目名称

    @Column(name = "projectindustry")
    private String projectIndustry; //项目行业

    @Column(name = "networkingway")
    private byte networkingWay; //联网方式(0: wifi，1: 移动蜂窝网络)

    @Column(name = "projectintroduce")
    private String projectIntroduce; //项目介绍

    @Column(name = "operating")
    private byte operating; //操作系统(0:linux；1:android；2:VxWorks  3:uc/OS；4:其他；5:无)

    @Column(name = "operator")
    private byte operator; //运营商(0:移动；1:电信；2:联通  3:其他)

    @Column(name = "equipaccessprotocol")
    private byte equipAccessProtocol; //设备接入协议(0:MQTT; 1:HTTP)

    @Column(name = "equipregistrationcode")
    private String equipRegistrationCode; //设备注册码

    @Column(name = "masterkey")
    private String masterKey; //获取项目下所有设备数据key

    @Column(name = "usercode")
    private byte userCode; //所属用户
}