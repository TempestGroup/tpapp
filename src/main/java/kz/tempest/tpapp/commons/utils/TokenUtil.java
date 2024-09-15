package kz.tempest.tpapp.commons.utils;

import io.jsonwebtoken.*;
import kz.tempest.tpapp.modules.person.models.Person;
import kz.tempest.tpapp.modules.person.services.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import java.sql.Date;
import java.util.HashMap;
import java.util.Map;

public class TokenUtil {

    private static final String SECRET_KEY = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private static final long EXPIRATION = 1000 * 60 * 60;

    public static String generateAccessToken(Claims claims){
        return Jwts
                .builder()
                .setClaims(claims)
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    public static String generateMobileToken(Map<String, String> claims){
        return Jwts
                .builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    public static String getAccessToken(String refreshToken){
        return refreshAccessToken(refreshToken);
    }

    public static String getMobileToken(String username) {
        Map<String, String> claims = new HashMap<>();
        claims.put("username", username);
        return generateMobileToken(claims);
    }

    public static String generateRefreshToken(Map<String, String> claims){
        return Jwts
                .builder()
                .setClaims(claims)
                .setExpiration(new Date((System.currentTimeMillis() + EXPIRATION) * 3))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    public static String getRefreshToken(String username) {
        Map<String,String> claims = new HashMap<>();
        claims.put("username", username);
        return generateRefreshToken(claims);
    }

    public static String refreshAccessToken(String refreshToken) {
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(SECRET_KEY)
                    .parseClaimsJws(refreshToken)
                    .getBody();
            return generateAccessToken(claims);
        } catch (JwtException e) {
            LogUtil.write(e);
            return null;
        }
    }

    public static boolean validateToken(String token){
        try {
            Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token);
            return true;
        } catch (MalformedJwtException | IllegalArgumentException | UnsupportedJwtException | ExpiredJwtException e) {
            LogUtil.write(e);
            return false;
        }
    }

    private static Claims getClaims(String token) {
        return Jwts
                .parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();
    }

    public static String getUsernameFromToken(String token) {
        return (String) getClaims(token).get("username");
    }

}

