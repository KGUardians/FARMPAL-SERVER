package farmeasy.server.config.login.jwt;

import farmeasy.server.config.login.auth.CustomUserDetailsService;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.Set;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final CustomUserDetailsService userDetailsService; // 사용자 정보를 제공하는 서비스
    private final JwtProperties jwtProperties; // JWT 관련 속성 클래스

    private static final Set<String> FILTERED_PATHS = Set.of("/market","/experience");

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {

        String[] excludePath = {"/auth/sign","/auth/refresh", "/swagger", "/swagger-ui", "/v3","/api-docs"};

        // 제외할 url 설정
        String path = request.getRequestURI();
        return Arrays.stream(excludePath).anyMatch(path::startsWith);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // 토큰을 가져와 저장할 변수
        String authToken = jwtProperties.extractTokenFromHeader(request);

        // JWT 토큰을 가지고 있는 경우, 토큰을 추출.
        if (authToken != null) {
            String username = getUsernameFromToken(authToken);
            if(isUserExists(username)){
                //토큰 검사
                authenticateUser(request,username,authToken);
            }
        }
        filterChain.doFilter(request, response);
    }
        private boolean isUserExists(String username){
            return username != null;
        }

        private String getUsernameFromToken(String authToken){
            try {
                return this.jwtProperties.getUsernameFromToken(authToken);
            } catch (IllegalArgumentException ex) {
                log.info("사용자 정보를 가져올 수 없습니다.");
            } catch (ExpiredJwtException ex) {
                log.info("토큰이 만료되었습니다");
            } catch (MalformedJwtException ex) {
                log.info("JWT 형식이 잘못되었습니다.");
            } catch (Exception e) {
                log.info("JWT 토큰을 가져올 수 없습니다.");
            }
            return null;
        }

        private void authenticateUser(HttpServletRequest req, String username, String authToken){
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
            this.jwtProperties.validateToken(authToken, userDetails);
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities());
            // 인증된 사용자 정보 설정
            authenticationToken
                    .setDetails(new WebAuthenticationDetailsSource().buildDetails(req));
            log.info("인증된 사용자 " + username + ", 보안 컨텍스트 설정");
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }

        private boolean filterHttpGetRequestByPath(HttpServletRequest request){
            String requestURI = request.getRequestURI();
            String method = request.getMethod();

            if (isFilteredPath(requestURI)) {
                return "GET".equals(method);
            }
            return false;
        }

        private boolean isFilteredPath(String requestURI){
            return FILTERED_PATHS.stream().anyMatch(requestURI::startsWith);
        }
}
