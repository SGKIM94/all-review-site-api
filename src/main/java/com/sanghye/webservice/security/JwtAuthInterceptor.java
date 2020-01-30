package com.sanghye.webservice.security;

import com.sanghye.webservice.domain.User;
import com.sanghye.webservice.exception.UnAuthenticationException;
import com.sanghye.webservice.service.UserService;
import lombok.AllArgsConstructor;
import org.apache.http.HttpHeaders;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@AllArgsConstructor
public class JwtAuthInterceptor extends HandlerInterceptorAdapter {
    private static final Logger log = LoggerFactory.getLogger(JwtAuthInterceptor.class);

    @Autowired
    private UserService userService;

    private TokenAuthenticationService tokenService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);

        log.info("Authorization : {}", authorization);

         if (authorization == null) {
            throw new UnAuthenticationException("토큰이 존재하지 않습니다.");
        }

        if (!tokenService.isAuthenticationUser(authorization)) {
            throw new UnAuthenticationException("토큰이 유효하지 않습니다.");
        }

        String userId = tokenService.getUserIdByClaim(authorization);

        if (!userService.isExistUser(userId)) {
            throw new UnAuthenticationException("존재하지 않는 계정입니다.");
        }

        User user = userService.findByUserId(userId);

        log.debug("Login Success : {}", user);
        request.getSession().setAttribute(HttpSessionUtils.USER_SESSION_KEY, user);

        return true;
    }
}
