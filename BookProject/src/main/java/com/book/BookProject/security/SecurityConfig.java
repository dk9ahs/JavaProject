package com.book.BookProject.security;

import com.book.BookProject.user.CustomOAuth2UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    private final UserServiceImpl userServiceImpl;
    private final CustomOAuth2UserService customOAuth2UserService;


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
                        .requestMatchers("/bestseller", "/bookdetail/**").permitAll()
                        .requestMatchers("/", "/register", "/signup", "/login", "/socialSignupCheck", "/findId", "/findPassword", "/IdCheck", "/NickCheck").permitAll()  // 추가
                        .requestMatchers("/member/**").hasAnyRole("USER", "ADMIN")
                        .requestMatchers("/admin/**").hasAnyRole("ADMIN")
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .loginProcessingUrl("/login")
                        .defaultSuccessUrl("/", true)
                        .failureUrl("/login?error=true")
                        .usernameParameter("username")
                        .passwordParameter("password")
                )
                .oauth2Login(oauth2 -> oauth2
                        .loginPage("/login")
                        .userInfoEndpoint(userInfo -> userInfo.userService(customOAuth2UserService))
                        .defaultSuccessUrl("/socialSignupCheck", true)
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
}