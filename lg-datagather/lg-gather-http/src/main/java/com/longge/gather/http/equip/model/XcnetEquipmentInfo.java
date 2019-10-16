package com.longge.gather.http.equip.model;
import lombok.Data;
import java.io.Serializable;
/**
 * @description 设备信息
 * @author jianglong
 * @create 2019-09-25
 **/
@Data
public class XcnetEquipmentInfo implements Serializable {

    private String equipCode; //设备编号

    private String equipName; //设备名称

    private String authentication; //鉴权信息(MQTT登录鉴权)

    private String equipDes; //设备描述

    private String equipPos; //设备位置(L,B)

    private String projectCode; //所属项目
}