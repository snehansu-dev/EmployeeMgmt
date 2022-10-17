package com.dailycode.employeesystemapi01.Utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.concurrent.TimeUnit;

@Component
public class JwtUtil {

    @Value("${app.secret}")
    private String secretKey;


    public boolean validateToken(String token, String DBUser){
        String tokenUsrNm = getUserName(token);
        return (DBUser.equals(tokenUsrNm) && !isTokenExpired(token) );
    }
    public boolean isTokenExpired(String token) {
        Date expDate = getExpireDate(token);
        return expDate.before(new Date(System.currentTimeMillis()));
    }

    public String getUserName(String token) {
        return getClaims(token).getSubject();
    }

    public Date getExpireDate(String token) {
        return getClaims(token).getExpiration();
    }
    // Read Claim
    public Claims getClaims(String token){
        return Jwts.parser()
                .setSigningKey(secretKey.getBytes())
                .parseClaimsJws(token)
                .getBody();
    }

    public String generateToken(String subject){
        return Jwts.builder()
                .setSubject(subject)
                .setIssuer("SNSTech")
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(500)))
                .signWith(SignatureAlgorithm.HS512,secretKey.getBytes())
                .compact();
    }


}

