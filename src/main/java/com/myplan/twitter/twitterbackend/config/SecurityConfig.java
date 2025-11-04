package com.myplan.twitter.twitterbackend.config;

import com.myplan.twitter.twitterbackend.security.CustomUserDetailService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    private CustomUserDetailService customUserDetailService;

    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return  new BCryptPasswordEncoder();
    }

//Login işlemi için
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception{
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http.csrf(csrf -> csrf.disable())  //csrf kayıtlı olan bilgileri başka bir sitede kullanılması. rest api olduğu için kapalı.(JSON ile haberleşildiğinden)
                .authorizeHttpRequests(auth-> auth.requestMatchers("/auth/register","/auth/login").permitAll()
                        .anyRequest().authenticated()).formLogin(form-> form.disable()).httpBasic(basic-> basic.disable());
        return http.build();
    }

}
