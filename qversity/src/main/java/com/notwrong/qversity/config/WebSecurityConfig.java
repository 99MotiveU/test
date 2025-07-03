package com.notwrong.qversity.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.web.client.RestTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository; // 추가 임포트



@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    private final JwtTokenProvider jwtTokenProvider;
    private final ClientRegistrationRepository clientRegistrationRepository;

    public WebSecurityConfig(JwtTokenProvider jwtTokenProvider, ClientRegistrationRepository clientRegistrationRepository) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.clientRegistrationRepository = clientRegistrationRepository;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable) // REST API이므로 CSRF 비활성화
                .cors(cors -> cors.configurationSource(corsConfigurationSource())) // CORS 설정 적용
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .formLogin(AbstractHttpConfigurer::disable) // Spring Security 기본 폼 로그인 비활성화
                .httpBasic(AbstractHttpConfigurer::disable) // HTTP Basic 인증 비활성화
                .authorizeHttpRequests(authorize -> authorize
                        // 로그인 API 엔드포인트는 인증 없이 접근 허용
                        .requestMatchers("/login/**","/oauth2/**").permitAll()
                        .requestMatchers("/oauth2/authorization/**", "/login/oauth2/code/**").permitAll()
                        // 정적 리소스 및 Swagger UI 등은 인증 없이 접근 허용
                        .requestMatchers("/css/**", "/js/**", "/images/**", "/swagger-ui/**", "/v3/api-docs/**").permitAll()
                        // 그 외 모든 요청은 인증 필요
                        .anyRequest().authenticated()
                );

        // JWT 인증 필터 추가
        http.addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class);

        http.oauth2Login(oauth2 -> oauth2
                .clientRegistrationRepository(this.clientRegistrationRepository) // 주입받은 Repository 사용
                // .authorizationEndpoint(authorization -> authorization.baseUri("/oauth2/authorization")) // 이 부분을 설정하면 Spring Security가 특정 경로를 OAuth2 시작점으로 강제하므로 제거합니다.
                .redirectionEndpoint(redirection -> redirection
                        .baseUri("/login/oauth2/code/*") // Google이 인가 코드를 보낼 콜백 URI (Spring Security가 받음)
                )
                // 성공 핸들러: Spring Security가 인가 코드를 받고 Access Token을 교환한 후 호출됩니다.
                // 여기서는 아무것도 하지 않거나, 기본 성공 페이지로 리다이렉션만 하도록 합니다.
                // 우리의 JWT 발급은 POST /login/google API에서 처리할 것입니다.
                .successHandler((request, response, authentication) -> {
                    // OAuth2 인증은 성공했으니, 이제 클라이언트(프론트엔드)는 인가 코드를 얻었습니다.
                    // 클라이언트(프론트엔드)가 우리의 /login/google API를 호출할 수 있도록 기본 리다이렉션만 수행합니다.
                    // 이 리다이렉션은 Google 인증이 끝났음을 알리는 역할만 합니다.
                    response.sendRedirect("http://localhost:8080"); // React 프론트엔드의 콜백 URL로 리다이렉션
                })
        );
        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOrigin("http://localhost:3000"); // React 프론트엔드 URL
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        source.registerCorsConfiguration("/**", config);
        return source;
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }
}