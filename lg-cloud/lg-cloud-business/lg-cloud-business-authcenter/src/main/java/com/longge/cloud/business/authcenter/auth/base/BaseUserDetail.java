package com.longge.cloud.business.authcenter.auth.base;
import com.longge.cloud.business.authcenter.feigin.model.BaseUserVo;
import org.springframework.security.core.CredentialsContainer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.*;
/**
 * @author: jianglong
 * @description:包装org.springframework.security.core.userdetails.User类，新增 baseUser
 * @date: 2019-01-14
 * */
public class BaseUserDetail implements UserDetails, CredentialsContainer {

	private static final long serialVersionUID = -7506813017307619894L;

	private final BaseUserVo baseUser;
    
    private final User user;

    public BaseUserDetail(BaseUserVo baseUser, User user) {
        this.baseUser = baseUser;
        this.user = user;
    }


    @Override
    public void eraseCredentials() {
        user.eraseCredentials();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return user.getAuthorities();
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    //账号是否未过期
    @Override
    public boolean isAccountNonExpired() {
        return user.isAccountNonExpired();
    }
    
   //账号是否未锁定
    @Override
    public boolean isAccountNonLocked() {
        return user.isAccountNonLocked();
    }

    // 账号凭证是否未过期
    @Override
    public boolean isCredentialsNonExpired() {
        return user.isCredentialsNonExpired();
    }

    //账户是否可用
    @Override
    public boolean isEnabled() {
        return user.isEnabled();
    }

    public BaseUserVo getBaseUser() {
        return baseUser;
    }
}
