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
                        .requestMatchers("/book", "/newbook", "/notablebooks", "/blogbestbooks", "/bookList", "/search").permitAll()
                        .requestMatchers("/bestseller", "/bookdetail/**", "/mypage/**").permitAll()
                        .requestMatchers("/", "/register", "/signup", "/login", "/find/**", "/IdCheck", "/NickCheck").permitAll()
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
}