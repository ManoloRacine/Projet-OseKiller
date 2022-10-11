package com.osekiller.projet.config;

import com.osekiller.projet.model.ERole;
import com.osekiller.projet.security.AuthTokenFilter;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class WebSecurityConfig {
    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authManager(HttpSecurity http, PasswordEncoder passwordEncoder, UserDetailsService userDetailsService)
            throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder)
                .and()
                .build();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, AuthTokenFilter authTokenFilter) throws Exception {
        http
                .cors().and()
                .csrf().disable()
                .authorizeHttpRequests(
                        (requests) -> requests
                                //Autoriser ses routes de base
                                .antMatchers("/ping","/sign-in","/sign-up").permitAll()
                                .antMatchers("/users/**","/user/**").hasAuthority(ERole.MANAGER.name())
                                .antMatchers(HttpMethod.PUT,"/student/{id}/cv").hasAuthority(ERole.STUDENT.name())
                                .antMatchers(HttpMethod.GET,"/student/{id}/cv").hasAnyAuthority(ERole.MANAGER.name(),ERole.STUDENT.name())
                                .antMatchers(HttpMethod.POST, "/companies/{id}/offers").hasAuthority(ERole.COMPANY.name())
                                .antMatchers("/student/{id}/cv/validate").hasAuthority(ERole.MANAGER.name())
                                //Le reste doivent être autentifié
                                .anyRequest().authenticated()
                )
                .addFilterBefore(authTokenFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}
