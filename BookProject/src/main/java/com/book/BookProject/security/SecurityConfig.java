package com.book.BookProject.security;

import com.book.BookProject.oauth2.CustomOAuth2UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.firewall.HttpFirewall;
import org.springframework.security.web.firewall.StrictHttpFirewall;

@Configuration
public class SecurityConfig {
    private final UserServiceImpl userServiceImpl;
    private final CustomOAuth2UserService customOAuth2UserService;
    private final CustomAuthenticationFailureHandler failureHandler;
    private final CustomAuthenticationSuccessHandler successHandler;
    private final CustomAuthenticationProvider customAuthenticationProvider; // CustomAuthenticationProvider 추가
    public SecurityConfig(UserServiceImpl userServiceImpl,
                          CustomOAuth2UserService customOAuth2UserService,
                          CustomAuthenticationFailureHandler failureHandler,
                          CustomAuthenticationSuccessHandler successHandler,
                          CustomAuthenticationProvider customAuthenticationProvider) { // CustomAuthenticationProvider 주입
        this.userServiceImpl = userServiceImpl;
        this.customOAuth2UserService = customOAuth2UserService;
        this.failureHandler = failureHandler;
        this.successHandler = successHandler;
        this.customAuthenticationProvider = customAuthenticationProvider; // CustomAuthenticationProvider 주입
    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable())  // CSRF 보호 비활성화
                .authorizeRequests(auth -> auth
                        .requestMatchers("/guest/**", "/css/**", "/js/**", "/images/**", "/webjars/**", "/static/**").permitAll()
                        .requestMatchers("/guest/unlock").permitAll()  // 계정 잠김 해제 페이지 접근 허용
                        .requestMatchers("/book", "/newbook", "/notablebooks", "/blogbestbooks", "/bookList", "/search", "/api/category", "/category/**", "/refundPayment").permitAll()  // API 경로 허용
                        .requestMatchers("/bestseller", "/bookdetail/**", "/mypage/**").permitAll()
                        .requestMatchers("/salesboard/**", "/inquiryboard/**").permitAll()
                        .requestMatchers("/order/**").authenticated()
                        .requestMatchers("/", "/register", "/signup", "/login", "/findId", "/findPassword", "/IdCheck", "/NickCheck").permitAll()  // 추가
                        .requestMatchers("/test").permitAll()
                        .requestMatchers("/guest/SocialSignup").permitAll()
                        .requestMatchers("/oauth2/**").permitAll()
                        .requestMatchers("/member/**").hasAnyRole("USER", "ADMIN")
                        .requestMatchers("/admin/**").hasAnyRole("ADMIN")
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .loginProcessingUrl("/login")
                        .defaultSuccessUrl("/", true)
                        .failureHandler(failureHandler)
                        .successHandler(successHandler)
                        .usernameParameter("username")
                        .passwordParameter("password")
                )
                .oauth2Login(oauth2 -> oauth2
                        .loginPage("/login")
                        .defaultSuccessUrl("/SocialLoginSuccess", true)
                        .userInfoEndpoint(userInfo -> userInfo.userService(customOAuth2UserService))
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login?logout=true")
                        .invalidateHttpSession(true)
                )
                .exceptionHandling(handling -> handling
                        .accessDeniedPage("/access-denied")
                )
                .build();
    }
    @Bean
    @Lazy
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http, PasswordEncoder passwordEncoder) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder =
                http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder
                .userDetailsService(userServiceImpl)
                .passwordEncoder(passwordEncoder);
        return authenticationManagerBuilder.build();
    }
    @Bean
    public HttpFirewall allowUrlEncodedSlashHttpFirewall() {
        StrictHttpFirewall firewall = new StrictHttpFirewall();
        firewall.setAllowUrlEncodedDoubleSlash(true); // 이중 슬래시 허용
        return firewall;
    }

    // 로그인 성공 후 Referer 헤더 기반으로 리다이렉트하는 SuccessHandler
//    @Bean
//    public AuthenticationSuccessHandler customAuthenticationSuccessHandler() {
//        return new SimpleUrlAuthenticationSuccessHandler() {
//            @Override
//            protected String determineTargetUrl(HttpServletRequest request, HttpServletResponse response) {
//                // 세션에서 redirectUrl 가져오기
//                String redirectUrl = (String) request.getSession().getAttribute("redirectUrl");
//
//                if (redirectUrl != null && !redirectUrl.isEmpty()) {
//                    System.out.println("Redirecting to: " + redirectUrl);
//                    // 세션에서 사용 후 삭제
//                    request.getSession().removeAttribute("redirectUrl");
//                    return redirectUrl;  // redirectUrl로 리다이렉트
//                }
//
//                // Referer 확인 (차선책)
//                String referer = request.getHeader("Referer");
//                System.out.println("Referer: " + referer);
//
//                if (referer != null && !referer.contains("/login")) {
//                    return referer;  // Referer가 로그인 페이지가 아니면 Referer로 리다이렉트
//                }
//
//                // 기본 경로로 리다이렉트
//                System.out.println("No valid Referer or redirectUrl found, redirecting to default target URL.");
//                return "/";
//            }
//        };
//    }

    // CORS 설정 추가
//    @Bean
//    public CorsConfigurationSource corsConfigurationSource() {
//        CorsConfiguration configuration = new CorsConfiguration();
//        configuration.setAllowedOrigins(Arrays.asList("http://localhost:8083"));  // 프론트엔드 도메인 허용
//        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));  // OPTIONS 추가
//        configuration.setAllowedHeaders(Arrays.asList("*"));
//        configuration.setAllowCredentials(true);
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        source.registerCorsConfiguration("/**", configuration);
//        return source;
//    }
}