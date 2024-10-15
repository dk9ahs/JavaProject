package com.book.BookProject.security;

import com.book.BookProject.oauth2.CustomOAuth2UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
    private final CustomOAuth2UserService customOAuth2UserService; // CustomOAuth2UserService 추가


    public SecurityConfig(UserServiceImpl userServiceImpl, CustomOAuth2UserService customOAuth2UserService) {
        this.userServiceImpl = userServiceImpl;
        this.customOAuth2UserService = customOAuth2UserService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable())  // CSRF 보호 비활성화
                .authorizeRequests(auth -> auth
                        .requestMatchers("/guest/**", "/css/**", "/js/**", "/images/**", "/webjars/**", "/static/**").permitAll() // 정적 리소스 경로 허용
                        .requestMatchers("/book", "/newbook", "/notablebooks", "/blogbestbooks", "/bookList", "/search").permitAll()  // API 경로 허용
                        .requestMatchers("/bestseller", "/bookdetail/**", "/mypage/**").permitAll()
                        .requestMatchers("/", "/register", "/signup", "/login", "/find/**","/IdCheck", "/NickCheck").permitAll()  // 추가
                        .requestMatchers("/guest/SocialSignup").permitAll()
                        .requestMatchers("/oauth2/**").permitAll()
                        .requestMatchers("/member/**").hasAnyRole("USER", "ADMIN")
                        .requestMatchers("/admin/**").hasAnyRole("ADMIN")
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login")  // 커스텀 로그인 페이지
                        .loginProcessingUrl("/login")  // 로그인 처리 URL
                        .defaultSuccessUrl("/", true)  // 로그인 성공 후 리다이렉트 경로
                        .failureUrl("/login?error=true")  // 로그인 실패 시 리다이렉트 경로
                        .usernameParameter("username")  // 로그인 폼의 username 파라미터
                        .passwordParameter("password")  // 로그인 폼의 password 파라미터
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
}