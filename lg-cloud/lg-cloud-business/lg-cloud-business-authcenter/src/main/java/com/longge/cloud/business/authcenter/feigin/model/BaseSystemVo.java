package com.longge.cloud.business.authcenter.feigin.model;
import lombok.Data;
import java.io.Serializable;
import java.util.Date;
/**
 * @author jianglong
 * @descrition 系统表VO
 * @date 2019-01-14
 * */
@Data
public class BaseSystemVo implements Serializable {

	private static final long serialVersionUID = 2875157701599793134L;

    private String id;

    private String systemName;

    private String projectName;

    private Integer active;

    private Integer sort;

    private Date createDate;

    private Date updateDate;
}