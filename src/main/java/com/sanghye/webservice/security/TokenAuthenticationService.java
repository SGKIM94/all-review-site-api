package com.sanghye.webservice.security;

import com.sanghye.webservice.UnAuthenticationException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;

@Service("tokenService")
public class TokenAuthenticationService {
    private static final String SALT = "63B75D39E3F6BFE72263F7C1145AC22E";
    private static final String HEADER_STRING = "Authorization";
    private static final String TOKEN_PREFIX = "Bearer";

    public void addAuthentication(HttpServletResponse response, String userId) {
        String Jwt = toJwtByUserId(userId);
        response.setHeader(HEADER_STRING, TOKEN_PREFIX + Jwt);
    }

    String toJwtByUserId(String userId) {
        return Jwts.builder()
                .setHeaderParam("type", "JWT")
                .setSubject(userId)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .signWith(SignatureAlgorithm.HS256, this.generateKey(SALT))
                .compact();
    }

    boolean isAuthenticationUser(String token) throws UnAuthenticationException {
        try {
            Jwts.parser()
                .setSigningKey(this.generateKey(SALT))
                .parseClaimsJws(token);

            return true;
        } catch(Exception e) {
            throw new UnAuthenticationException("토큰이 올바르지 않습니다.");
        }
    }

    byte[] generateKey(String salt){
        return salt.getBytes();
    }
}
