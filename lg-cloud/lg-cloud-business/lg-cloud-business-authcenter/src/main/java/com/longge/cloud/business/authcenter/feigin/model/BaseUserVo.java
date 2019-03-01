package com.longge.cloud.business.authcenter.feigin.model;
import lombok.Data;
import java.io.Serializable;
import java.util.Date;
/**
 * @author jianglong
 * @descrition 用户表VO
 * @date 2018-12-13
 * */
@Data
public class BaseUserVo  implements Serializable {

	private static final long serialVersionUID = 8176990396604853389L;

    private String id;

    private String userName;

    private String password;

    private String phone;

    private String gender;

    private Integer age;

    private Integer active;

    private Date createDate;

    private Date updateDate;
}