package com.vueblog.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Slf4j
@Component
public class JwtUtils {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.lifetime}")
    private Duration lifetime;

    public String generateGwt(UserDetails userDetails) {
        HashMap<String, Object> claims = new HashMap<>();

        List<String> roles = userDetails
                .getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .toList();

        claims.put("authorities", roles);

        Date now = new Date();

         return Jwts
                    .builder()
                    .setClaims(claims)
                    .setSubject(userDetails.getUsername())
                    .signWith(SignatureAlgorithm.HS256, secret)
                    .setIssuedAt(now)
                    .setExpiration(new Date(now.getTime() + lifetime.toMillis()))
                    .compact();
    }

    public String getUsername(String token) {
        return getClaims(token).getSubject();
    }

    public List<SimpleGrantedAuthority> getAuthorities(String token) {
        Object claim = getClaims("authorities");
        if (claim instanceof List<?>) {
            return ((List<?>) claim)
                    .stream()
                    .filter(String.class::isInstance)
                    .map(String.class::cast)
                    .map(SimpleGrantedAuthority::new)
                    .toList();
        } else {
            log.warn("Claim \"authorities\" is not a list (JwtUtils -> getAuthorities)");
            return new ArrayList<>();
        }
    }

    private Claims getClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(secret)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

}
