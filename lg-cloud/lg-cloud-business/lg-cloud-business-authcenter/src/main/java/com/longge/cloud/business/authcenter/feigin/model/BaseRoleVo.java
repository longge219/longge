package com.longge.cloud.business.authcenter.feigin.model;
import lombok.Data;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
/**
 * @author jianglong
 * @descrition 角色VO
 * @date 2019-01-14
 * */
@Data
public class BaseRoleVo implements Serializable {
	
	private static final long serialVersionUID = -5995007342001236113L;

    private String id;

    private String roleCode;

    private String roleName;

    private Date createDate;
    
    private Date updateDate;

    private List<BaseModuleResourcesVo> modules;
}