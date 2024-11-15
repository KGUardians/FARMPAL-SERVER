package farmeasy.server.config.login;

import farmeasy.server.config.login.auth.CustomAuthProvider;
import farmeasy.server.config.login.auth.CustomUserDetailsService;
import farmeasy.server.config.login.jwt.JwtAuthenticationEntryPoint;
import farmeasy.server.config.login.jwt.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final CustomUserDetailsService userDetailsService;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager() {
        return new ProviderManager(customAuthProvider());
    }

    @Bean
    public CustomAuthProvider customAuthProvider(){
        return new CustomAuthProvider(userDetailsService,passwordEncoder());
    }

    //권한 계층 설정
    @Bean
    public RoleHierarchy roleHierarchy(){
        RoleHierarchyImpl roleHierarchy = new RoleHierarchyImpl();

        roleHierarchy.setHierarchy("ROLE_FARMER > ROLE_USER\n");
        roleHierarchy.setHierarchy("ROLE_ADMIN > ROLE_FARMER\n");

        return roleHierarchy;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http
                .authenticationManager(authenticationManager())
                .authenticationProvider(customAuthProvider());

        http
                .httpBasic(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable);

        http
                .authorizeHttpRequests(authorize
                ->authorize
                        .requestMatchers("/","/auth/sign-in","/auth/sign-up","/image/**","/auth/refresh").permitAll()
                        .requestMatchers("/swagger","/swagger-ui/**","/v3/**").permitAll()
                        .requestMatchers(HttpMethod.GET,"/experience/**","/market/**","/community/**").permitAll()
                        .requestMatchers(HttpMethod.POST,"/experience","/market").hasRole("FARMER")
                        .requestMatchers(HttpMethod.POST,"/community?type=NOTICE").hasRole("ADMIN")
                        .anyRequest().authenticated());

        http
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .exceptionHandling(exception -> exception.authenticationEntryPoint(jwtAuthenticationEntryPoint))
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

}
