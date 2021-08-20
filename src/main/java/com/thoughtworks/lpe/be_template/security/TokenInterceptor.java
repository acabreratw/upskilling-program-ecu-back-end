package com.thoughtworks.lpe.be_template.security;

import com.thoughtworks.lpe.be_template.config.UserData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class TokenInterceptor implements HandlerInterceptor {

    @Autowired
    private UserEnvironment userEnvironment;

    @Autowired
    private TokenDecoder tokenDecoder;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {

        userEnvironment.setUserData(new UserData());
        String decode = tokenDecoder.getTokenPayload(request.getHeader("Authorization"));
        UserData userData = UserData
                .builder()
                .id(tokenDecoder.getCustomPropertyFromToken(decode, "userId"))
                .email(tokenDecoder.getCustomPropertyFromToken(decode, "email"))
                .fullName(tokenDecoder.getCustomPropertyFromToken(decode, "name"))
                .build();
        userEnvironment.setUserData(userData);

        return true;
    }

}
