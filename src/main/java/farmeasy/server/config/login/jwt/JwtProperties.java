package farmeasy.server.config.login.jwt;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.io.Serializable;

@Getter
@Configuration
public class JwtProperties implements Serializable {
    private static final long serialVersionUID = -2550185165626007488L;

    @Value("${jwt.accessTokenExpirationTime}")
    private Integer accessTokenExpirationTime; // access token 만료 시간

    @Value("${jwt.refreshTokenExpirationTime}")
    private Integer refreshTokenExpriationTime; // refresh token 만료 시간

    @Value("${jwt.header}")
    private String header; // HTTP 요청 헤더에서 JWT를 찾을 헤더 이름 -> "Authorization"

    @Value("${jwt.prefix}")
    private String prefix; // JWT가 시작하는 접두사 -> "Bearer"

    @Value("${jwt.secret}")
    private String secretKey;

}