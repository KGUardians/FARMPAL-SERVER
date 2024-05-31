package com.example.farmeasyserver.config.login.jwt;

import com.example.farmeasyserver.dto.TokenDto;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.security.Key;

@Component
public class JwtProperties implements Serializable {
    private static final long serialVersionUID = -2550185165626007488L;

    @Value("${jwt.accessTokenExpirationTime}") private Integer accessTokenExpirationTime; // access token 만료 시간
    @Value("${jwt.refreshTokenExpirationTime}") private Integer refreshTokenExpriationTime; // refresh token 만료 시간

    private final Key key; // 비밀키를 Key 형태로 변환

    // 생성자에서 비밀 키를 초기화합니다.
    // 빈이 생성되고 주입을 받은 후에 secret값을 Base64 Decode해서 key 변수에 할당하기 위해
    public JwtProperties(@Value("${jwt.secret}") String secretKey) {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    // JWT 토큰에서 사용자 이름 추출
    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    // JWT 토큰에서 만료 날짜 추출
    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    // JWT 토큰에서 클레임 추출
    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    // JWT 토큰에서 모든 클레임 추출
    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    // JWT 토큰 만료 여부 확인
    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    // 사용자 정보를 기반으로 JWT 토큰 생성
    public TokenDto generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        return doGenerateToken(claims, userDetails.getUsername());
    }

    // JWT 토큰 생성
    private TokenDto doGenerateToken(Map<String, Object> claims, String subject) {
        String accessToken = generateToken(claims,subject,accessTokenExpirationTime);
        String refreshToken = generateToken(claims,subject,refreshTokenExpriationTime);
        return new TokenDto(accessToken, refreshToken, subject);
    }

    private String generateToken(Map<String, Object> claims, String subject, Integer expirationTime){
        return Jwts.builder().setClaims(claims).setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime * 1000))
                .signWith(key,SignatureAlgorithm.HS512)
                .compact();
    }

    // JWT 토큰 유효성 검사
    public void validateToken(String token, UserDetails userDetails) {
        final String username = getUsernameFromToken(token);
        if(!username.equals(userDetails.getUsername())){
            throw new SecurityException("토큰이 유효하지 않습니다.");
        }
        if(isTokenExpired(token)){
            throw new SecurityException("토큰이 만료되었습니다.");
        }
    }

    
}