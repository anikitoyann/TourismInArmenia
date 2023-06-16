package com.example.tourisminarmenia.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
public class SpringSecurityConfig {

    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf().disable()
                .authorizeHttpRequests()
                .requestMatchers(HttpMethod.GET, "/","/regions/**","/item/**","/tour/**").permitAll()
                .requestMatchers("/css/**").permitAll()
                .requestMatchers("/images").permitAll()
                .requestMatchers("/user/register").permitAll()
                .requestMatchers("/contactUs").permitAll()
              //  .requestMatchers("/item/**").hasAnyAuthority("ADMIN", "USER")
            //    .requestMatchers("/user/admin").hasAuthority("ADMIN")
                .anyRequest().authenticated()
                .and()
                .formLogin()
                   .loginPage("/customLogin")
                   .defaultSuccessUrl("/customSuccessLogin")
                   .loginProcessingUrl("/login")
                   .permitAll()
                .and()
                .logout()
                   .permitAll()
                   .logoutSuccessUrl("/");

        return httpSecurity.build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder);
        return authenticationProvider;
    }
}
