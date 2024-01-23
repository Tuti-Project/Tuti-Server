package com.tuti.auth.infrastructure;

import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtTokenProvider {
    @Value("${jwt.access-token.expire-length}")
    private long accessTokenExpireLength;
    @Value("${jwt.refresh-token.expire-length}")
    private long refreshTokenExpireLength;
    @Value("${jwt.token.secret-key}")
    private String secretKey;

    public String createAccessToken(Long payload) {
        return createToken(payload, accessTokenExpireLength);
    }

    public String createRefreshToken(Long payload) {
        return createToken(payload, refreshTokenExpireLength);
    }

    public String createToken(Long payload, long expireLength) {
        Claims claims = Jwts.claims().setSubject(payload.toString());
        Date now = new Date();
        Date validity = new Date(now.getTime() + expireLength);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    public String getPayload(String token) {
        try {
            return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
        } catch (ExpiredJwtException e) {
            return e.getClaims().getSubject();
        } catch (JwtException e) {
            throw new RuntimeException("유효하지 않은 토큰입니다.");
        }
    }

    public boolean validateToken(String token) {
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            validateTokenExpiration(claims.getBody().getExpiration());
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    private void validateTokenExpiration(Date tokenExpirationDate) {
        if (tokenExpirationDate.before(new Date())) {
            throw new IllegalArgumentException("만료된 토큰입니다.");
        }
    }

}
