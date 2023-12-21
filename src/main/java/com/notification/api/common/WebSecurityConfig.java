package com.notification.api.common;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class WebSecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(registry -> registry
                                .requestMatchers("/**").permitAll()
//                        .requestMatchers("/ws/notifications").hasAuthority("SCOPE_notifications.read")
//                                .requestMatchers("/ws/notifications").permitAll()
//                                .requestMatchers(POST, "/notifications/**").permitAll()
                )
                .build();
    }

}