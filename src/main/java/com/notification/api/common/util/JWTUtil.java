package com.notification.api.common.util;

import com.notification.api.common.domain.AppUserDetails;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.*;

public class JWTUtil {
    private static final String JWT_SIGN_KEY = "mysignkey"; // TODO MODIFY

    public static String createToken(AppUserDetails user, int expireMinutes) {
        Map<String, Object> claims = new HashMap<>();

        List<String> authList = new ArrayList<>();
        user.getAuthorities().forEach(auth -> authList.add(auth.getAuthority()));

        claims.put("name", user.getUsername());
        claims.put("auth", authList);

        Date dt = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(dt);
        c.add(Calendar.MINUTE, expireMinutes);
        dt = c.getTime();

        return Jwts.builder().setClaims(claims).setExpiration(dt)
                .signWith(SignatureAlgorithm.HS512, JWT_SIGN_KEY).compact();
    }

    @SuppressWarnings("unchecked")
    public static AppUserDetails verifyToken(String token) {
        Jws<Claims> jwt = signAndParseJWT(token);
        if (jwt == null) return null;
        Claims token_body = jwt.getBody();

        List<String> auth_list = (List<String>) token_body.get("auth");
        String name = (String) token_body.get("name");

        AppUserDetails user = new AppUserDetails();
        user.setAuthority(setAuthorities(auth_list));
        user.setUsername(name);

        return user;
    }

    public static Jws<Claims> signAndParseJWT(String token) {
        try {
            return Jwts.parser().setSigningKey(JWT_SIGN_KEY).parseClaimsJws(token);
        } catch (Exception e) {
            // ExpiredJwtException, UnsupportedJwtException, MalformedJwtException, SignatureException, IllegalArgumentException
            return null;
        }
    }

    public static Collection<GrantedAuthority> setAuthorities(List<String> auth_list) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        auth_list.stream().forEach(authority -> authorities.add(new SimpleGrantedAuthority(authority)));
        return authorities;
    }
}
