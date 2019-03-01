package com.longge.cloud.business.authcenter.code;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

public class CusBCryptPasswordEncoder extends BCryptPasswordEncoder{
	@Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        String presentedPassword =passwordEncoder.encode(encodedPassword);
        boolean res = passwordEncoder.matches(rawPassword, presentedPassword);
        System.out.println("比对结果" + res);
        return res;
    }
}
