package com.cs.calendarback.config;


import com.cs.calendarback.calendar.service.RefreshService;
import com.cs.calendarback.jwt.CustomLogoutFilter;
import com.cs.calendarback.jwt.JWTFilter;
import com.cs.calendarback.jwt.JWTUtil;
import com.cs.calendarback.jwt.LoginFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutFilter;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final AuthenticationConfiguration authenticationConfiguration;

    private final JWTUtil jwtUtil;

    private final RefreshService refreshService;

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }


    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        //csrf disable
        http.csrf(AbstractHttpConfigurer::disable);
        //From 로그인 방식 disable
        http.formLogin(AbstractHttpConfigurer::disable);
        //http basic 인증 방식 disable
        http.httpBasic(AbstractHttpConfigurer::disable);

        http.addFilterBefore(new JWTFilter(jwtUtil), LoginFilter.class);
        //경로별 인가 작업
        http.authorizeHttpRequests((auth) -> auth.requestMatchers("/api/v1/**", "/", "/login","/reissue").permitAll().requestMatchers("/admin").hasRole("ADMIN").anyRequest().authenticated());
        http.addFilterAt(new LoginFilter(authenticationManager(authenticationConfiguration), jwtUtil, refreshService), UsernamePasswordAuthenticationFilter.class);

        http.addFilterBefore(new CustomLogoutFilter(jwtUtil, refreshService), LogoutFilter.class);
        //세션 설정
        http.sessionManagement((session) -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        return http.build();
    }
}
