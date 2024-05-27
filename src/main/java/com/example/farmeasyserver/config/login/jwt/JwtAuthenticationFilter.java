package com.example.farmeasyserver.config.login.jwt;

import com.example.farmeasyserver.config.login.auth.CustomUserDetailsService;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.Set;

/**
 * JWT 토큰의 유효성을 검사하고, 인증
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final CustomUserDetailsService userDetailsService; // 사용자 정보를 제공하는 서비스
    private final JwtProperties jwtProperties; // JWT 관련 속성 클래스

    @Value("${jwt.header}") private String HEADER_STRING; // HTTP 요청 헤더에서 JWT를 찾을 헤더 이름 -> "Authorization"
    @Value("${jwt.prefix}") private String TOKEN_PREFIX; // JWT가 시작하는 접두사 -> "Bearer"

    private static final Set<String> FILTERED_PATHS = Set.of("/market","/experience");

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String[] excludePath = {"/", "/community/list",
                "/auth/sign-in", "/auth/sign-up",
                "/swagger", "/swagger-ui/**", "/v3/**"};
        // 제외할 url 설정
        String path = request.getRequestURI();
        return Arrays.asList(excludePath).contains(path);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        if(filterHttpGetRequestByPath(request)){
            filterChain.doFilter(request,response);
        }
        else{
            Thread currentThread = Thread.currentThread();
            log.info("현재 실행 중인 스레드: " + currentThread.getName());

            // 토큰을 가져와 저장할 변수
            String header = request.getHeader(HEADER_STRING);
            String authToken = extractTokenFromHeader(header);

            // JWT 토큰을 가지고 있는 경우, 토큰을 추출.
            if (authToken != null) {
                String username = getUsernameFromToken(authToken);
                if(username != null && SecurityContextHolder.getContext().getAuthentication() == null){
                    //토큰 검사
                    authenticateUser(request,username,authToken);
                } else {
                    log.info("사용지 이름이 null이거나 컨텍스트가 null입니다");
                }
            }
            //JWT 토큰이 없는 경우
            else {
                log.info("JWT가 Bearer로 시작하지 않습니다");
            }
            filterChain.doFilter(request, response);
        }


    }

    private String extractTokenFromHeader(String header) {
        if (header != null && header.startsWith(TOKEN_PREFIX)) {
            return header.replace(TOKEN_PREFIX, " ");
        }
        return null;
    }

    private String getUsernameFromToken(String authToken){
        try {
            return this.jwtProperties.getUsernameFromToken(authToken);
        } catch (IllegalArgumentException ex) {
            log.info("사용자 ID 가져오기 실패");
        } catch (ExpiredJwtException ex) {
            log.info("토큰 만료");
        } catch (MalformedJwtException ex) {
            log.info("잘못된 JWT !!");
        } catch (Exception e) {
            log.info("JWT 토큰 가져오기 실패 !!");
        }
        return null;
    }

    private void authenticateUser(HttpServletRequest req, String username, String authToken){
        UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
        // JWT 토큰 유효성 검사
        if (this.jwtProperties.validateToken(authToken, userDetails)) {
            // 인증 정보 생성
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

            // 인증된 사용자 정보 설정
            authenticationToken
                    .setDetails(new WebAuthenticationDetailsSource().buildDetails(req));
            log.info("인증된 사용자 " + username + ", 보안 컨텍스트 설정");
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }
        else {
            log.info("잘못된 JWT 토큰 !!");
        }
    }

    private boolean filterHttpGetRequestByPath(HttpServletRequest request){
        String requestURI = request.getRequestURI();
        String method = request.getMethod();

        if (isFilteredPath(requestURI)) {
            return "GET".equals(method);
        }
        return false;
    }

    private boolean isFilteredPath(String requestURI) {
        return FILTERED_PATHS.stream().anyMatch(requestURI::startsWith);
    }
}