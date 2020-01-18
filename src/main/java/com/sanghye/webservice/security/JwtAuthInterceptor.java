package com.sanghye.webservice.security;

import com.sanghye.webservice.exception.UnAuthenticationException;
import lombok.AllArgsConstructor;
import org.apache.http.HttpHeaders;
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
            throws Exception {String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);

        log.info("Authorization : {}", authorization);

         if (authorization == null) {
            throw new UnAuthenticationException("토큰이 존재하지 않습니다.");
        }

        if (!tokenService.isAuthenticationUser(authorization)) {
            throw new UnAuthenticationException("토큰이 유효하지 않습니다.");
        }

        return true;
    }
}
