package com.sanghye.webservice.security;

import com.sanghye.webservice.exception.UnAuthenticationException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;

@Service("tokenService")
public class TokenAuthenticationService {
    private static final String SALT = "63B75D39E3F6BFE72263F7C1145AC22E";
    private static final String HEADER_STRING = "Authorization";

    public void addAuthentication(HttpServletResponse response, String userId) {
        String Jwt = toJwtByUserId(userId);
        response.setHeader(HEADER_STRING, Jwt);
    }

    public String toJwtByUserId(String userId) {
        return Jwts.builder()
                .setHeaderParam("type", "JWT")
                .setSubject(userId)
                .claim("userId", userId)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .signWith(SignatureAlgorithm.HS256, this.generateKey(SALT))
                .compact();
    }

    boolean isAuthenticationUser(String token) throws UnAuthenticationException {
        try {
            getJwtClaims(token);

            return true;
        } catch(Exception e) {
            throw new UnAuthenticationException("토큰이 올바르지 않습니다.");
        }
    }

    byte[] generateKey(String salt){
        return salt.getBytes();
    }

    private Jws<Claims> getJwtClaims(String token) {
        return Jwts.parser()
                .setSigningKey(this.generateKey(SALT))
                .parseClaimsJws(token);
    }

    String getUserIdByClaim(String token) {
        Jws<Claims> claims = getJwtClaims(token);

        return claims.getBody().get("userId").toString();
    }
}
