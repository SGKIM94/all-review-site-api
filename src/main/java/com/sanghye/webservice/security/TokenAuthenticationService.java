package com.sanghye.webservice.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;

public class TokenAuthenticationService {
    private static final String SALT = "63B75D39E3F6BFE72263F7C1145AC22E";
    private static final String HEADER_STRING = "Authorization";
    private static final String TOKEN_PREFIX = "Bearer";

    public void addAuthentication(HttpServletResponse response, String userId) {
        String Jwt = Jwts.builder()
                .setSubject(userId)
                .setExpiration(new Date(System.currentTimeMillis()))
                .signWith(SignatureAlgorithm.HS512, generateKey())
                .compact();

        response.addHeader(HEADER_STRING, TOKEN_PREFIX + " " + Jwt);
    }

    private byte[] generateKey(){
        return SALT.getBytes();
    }
}
