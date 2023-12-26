package com.notification.api.common.security;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.authentication.AuthenticationManagerBeanDefinitionParser;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@AllArgsConstructor
@EnableGlobalMethodSecurity(securedEnabled = true)
public class WebSecurityConfig {

    private RestDeniedHandler restDeniedHandler;
    private RestAuthenticationEntryPoint restAuthenticationEntryPoint;
    private AuthenticationTokenProcessingFilter authenticationTokenProcessingFilter;


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .httpBasic(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(manage -> manage.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .exceptionHandling(handler -> handler.authenticationEntryPoint(restAuthenticationEntryPoint)
                        .accessDeniedHandler(restDeniedHandler))
                .headers(header -> header.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable))
                .authorizeHttpRequests(registry ->
                        registry
                                .requestMatchers("/h2-console/**").permitAll()
                                .requestMatchers("/ws/**").permitAll()
                                .requestMatchers("/topic/**", "/queue/**", "/pub/**", "/test/**").permitAll()
                                .anyRequest().authenticated()
                )
                .formLogin(AbstractHttpConfigurer::disable)
                .addFilterBefore(authenticationTokenProcessingFilter, UsernamePasswordAuthenticationFilter.class)
                .authenticationProvider(new AuthenticationManagerBeanDefinitionParser.NullAuthenticationProvider());
        return http.build();
    }

}