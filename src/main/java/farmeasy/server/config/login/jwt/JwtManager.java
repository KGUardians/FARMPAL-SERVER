package farmeasy.server.config.login.jwt;

import farmeasy.server.config.login.jwt.dto.TokenDto;
import farmeasy.server.user.domain.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtManager {

    private final JwtProperties jwtProperties;
    private final Key key; // 비밀키를 Key 형태로 변환

    // 생성자에서 비밀 키를 초기화합니다.
    // 빈이 생성되고 주입을 받은 후에 secret값을 Base64 Decode해서 key 변수에 할당하기 위해
    public JwtManager(JwtProperties jwtProperties) {
        this.jwtProperties = jwtProperties;
        byte[] keyBytes = Decoders.BASE64.decode(jwtProperties.getSecretKey());
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
        String username = userDetails.getUsername();
        String accessToken = generateToken(claims, username, jwtProperties.getAccessTokenExpirationTime());
        String refreshToken = generateToken(claims, username, jwtProperties.getRefreshTokenExpriationTime());
        return new TokenDto(accessToken, refreshToken, username);
    }

    private String generateToken(Map<String, Object> claims, String subject, Integer expirationTime){
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime * 1000))
                .signWith(key, SignatureAlgorithm.HS512)
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

    //헤더에서 토큰 추출
    public String extractTokenFromHeader(HttpServletRequest request){
        String header = request.getHeader(jwtProperties.getHeader());
        if (header != null && header.startsWith(jwtProperties.getPrefix())) {
            return header.replace(jwtProperties.getPrefix(), "").trim();
        } else {
            throw new SecurityException("잘못된 토큰 형식입니다.");
        }
    }

    private String extractHeader(HttpServletRequest request){
        return request.getHeader(jwtProperties.getHeader());
    }

    public ResponseEntity<String> refreshToken(Cookie[] cookies, User user){
        String refreshToken = getRefreshToken(cookies);

        if (refreshToken != null){
            validateToken(refreshToken, user);
            validateRefreshToken(refreshToken, user.getRefreshToken());
            return ResponseEntity.ok(generateToken(user).getAccessToken());
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid refresh token");
        }

    }

    public String getRefreshToken(Cookie[] cookies){
        String refreshToken = null;

        if(cookies != null){
            for(Cookie cookie : cookies){
                if(cookie.getName().equals("refreshToken")){
                    refreshToken = cookie.getValue();
                }
            }
        }

        return refreshToken;
    }

    private void validateRefreshToken(String refreshToken, String userRefreshToken) {
        if (!refreshToken.equals(userRefreshToken)) {
            throw new SecurityException("Refresh token does not match");
        }
    }
}
