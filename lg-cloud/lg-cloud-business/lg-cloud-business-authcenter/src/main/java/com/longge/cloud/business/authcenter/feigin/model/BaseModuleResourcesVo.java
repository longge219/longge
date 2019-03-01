package com.longge.cloud.business.authcenter.feigin.model;
import lombok.Data;
import java.io.Serializable;
import java.util.Date;
/**
 * @author jianglong
 * @descrition 资源VO
 * @date 2019-01-14
 * */
@Data
public class BaseModuleResourcesVo implements Serializable {

	private static final long serialVersionUID = 8385824880998440106L;

	 //资源ID
    private String id;

    //模块名称
    private String moduleName;

    //模块编号
    private String moduleCode;

    //模块路径
    private String modulePath;

    //父ID
    private String parentId;

    //模块图标
    private String moduleIcon;

    //请求方式
    private String httpMethod;

    //0 否，1 是
    private Integer isOperating;

    //排序
    private Integer sort;

    //激活状态
    private Integer active;

    //创建时间
    private Date createDate;

    //更新时间
    private Date updateDate;
}