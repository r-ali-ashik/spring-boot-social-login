package com.rali.security;

import com.rali.config.AppProperties;
import com.rali.entity.User;
import com.rali.entity.Role;
import com.rali.exception.ApiException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
public class OAuth2TokenService {


    private final AppProperties appProperties;

    public OAuth2TokenService(AppProperties appProperties) {
        this.appProperties = appProperties;
    }

    public String createToken(User user, String issuer) {

        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + appProperties.getAuth().getTokenExpirationMsec());
        Key signingKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(appProperties.getAuth().getTokenSecret()));


        return Jwts.builder()
                .subject(String.valueOf(user.getId()))
                .issuer(issuer)
                .issuedAt(now)
                .expiration(expiryDate)
                .claims(getClaims(user))
                .signWith(signingKey)
                .compact();
    }

    public String createRefreshToken(User user, String issuer) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + appProperties.getAuth().getRefreshTokenExpirationMsec());
        Key signingKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(appProperties.getAuth().getTokenSecret()));

        return Jwts.builder()
                .subject(String.valueOf(user.getId()))
                .issuer(issuer)
                .issuedAt(now)
                .expiration(expiryDate)
                .claims(getClaims(user))
                .signWith(signingKey)
                .compact();
    }

    public Claims parseToken(String token) throws ApiException {
        try {
            SecretKey signingKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(appProperties.getAuth().getTokenSecret()));

            return Jwts.parser()
                    .verifyWith(signingKey)  // Use verifyWith() instead of setSigningKey()
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();  // getBody() → getPayload() in new API

        } catch (Exception e) {
            throw new ApiException("INVALID_TOKEN", HttpStatus.UNAUTHORIZED);
        }
    }

    private Map<String, Object> getClaims(User user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", user.getId());
        claims.put("firstName", user.getFirstName());
        claims.put("lastName", user.getLastName());
        claims.put("email", user.getEmail());
        claims.put("mobile", user.getMobile());
        claims.put("roles", user.getRoles().stream().map(Role::getName).collect(Collectors.toSet()));
        return claims;
    }
}
