package com.longge.cloud.business.authcenter.feigin.model;
import lombok.Data;
import java.io.Serializable;
/**
 * @author jianglong
 * @descrition 认证客户端配置VO
 * @date 2018-12-13
 * */
@Data
public class OauthClientDetailsVo implements Serializable {

	private static final long serialVersionUID = 5741028338255960054L;

    private String clientId;

    private String resourceIds;

    private String clientSecret;

    private String scope;

    private String authorizedGrantTypes;

    private String webServerRedirectUri;

    private String authorities;

    private Integer accessTokenValidity;

    private Integer refreshTokenValidity;

    private String additionalInformation;

    private String autoapprove;

    private String name;
}