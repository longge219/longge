package com.longge.gather.gnss.server.model;
import lombok.Data;
/**
 * @description 接收机信息
 * @author jianglong
 * @create 2018-06-19
 **/
@Data
public class SiteInfo {

    public SiteInfo(String siteNo){
        this.siteNo = siteNo;
    }

    private String siteNo; //接收机编号

    private byte siteType;//0基准站；1监测站

    /**地心坐标X*/
    private Double lat;

    /**地心坐标Y*/
    private Double lng;

    /**地心坐标Z*/
    private Double alt;



}
