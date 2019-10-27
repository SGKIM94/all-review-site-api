package com.sanghye.webservice.security;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@AllArgsConstructor
public class JwtAuthInterceptor extends HandlerInterceptorAdapter {
    private static final Logger log = LoggerFactory.getLogger(JwtAuthInterceptor.class);

    private TokenAuthenticationService tokenService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        String authorization = request.getHeader("Authorization");

        log.info("Authorization : {}", authorization);

        if (authorization == null) {
            return true;
        }

        if (!tokenService.isAuthenticationUser(authorization)) {
            return true;
        }

        return true;
    }
}
