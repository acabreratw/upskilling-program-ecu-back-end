package com.thoughtworks.lpe.be_template.security;

import com.thoughtworks.lpe.be_template.util.UserData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class TokenInterceptor implements HandlerInterceptor {

    @Autowired
    private UserData userData;

    @Autowired
    private TokenDecoder tokenDecoder;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {

        String token = tokenDecoder.getTokenPayload(request.getHeader("Authorization"));

        userData.setId(tokenDecoder.getCustomPropertyFromToken(token, "userId"));
        userData.setEmail(tokenDecoder.getCustomPropertyFromToken(token, "email"));
        userData.setFullName(tokenDecoder.getCustomPropertyFromToken(token, "name"));

        return true;
    }

}
