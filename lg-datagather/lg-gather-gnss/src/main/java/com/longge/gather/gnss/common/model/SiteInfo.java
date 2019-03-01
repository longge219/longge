package com.longge.gather.gnss.common.model;
import java.io.Serializable;
import java.util.Date;
import lombok.Getter;
import lombok.Setter;

/**接收机信息*/
@Setter
@Getter
public class SiteInfo implements Serializable{
	
	private static final long serialVersionUID = 5267240330758286052L;
	
	private String  siteNo;
	
	/**接收机关联基准站编号*/
	private String  mSiteNo;
	
	/**接收机名称*/
	private String siteName;
	
	/**接收机类型 0--基准站；1--监测站*/
    private String dictCode;
	
	/**项目ID*/
    private Integer projectId;
	
	/**接收机IP*/
    private String netIp;
	
	/**接收机端口*/
    private Integer netPort;
	
	/**地心坐标X*/
    private Double lat;
	
	/**地心坐标Y*/
    private Double lng;
	
	/**地心坐标Z*/
    private Double alt;
	
	
	/**X方向速度*/
    private Double latSpeed;
	
	/**Y方向速度*/
    private Double lngSpeed;

	/**Z方向速度*/
    private Double altSpeed;
	
	/**网页显示X坐标*/
    private Double showXpos;
    
	/**网页显示Y坐标*/
    private Double showYpos;
    
	/**终端在线状态*/
    private Boolean online;
    
    /**告警状态*/
    private Boolean warnState;
    
    /**设备创建时间*/
    private Date createTime;
    
    /**启用状态（1-启用 0-停用)*/
    private Byte state;
    
    /**是否逻辑删除（0-否 1-是)*/
    private Boolean isDelete;
}
