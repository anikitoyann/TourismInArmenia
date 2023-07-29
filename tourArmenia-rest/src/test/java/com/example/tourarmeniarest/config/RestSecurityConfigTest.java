package com.example.tourarmeniarest.config;

import com.example.tourarmeniarest.filter.JWTAuthenticationTokenFilter;
import com.example.tourarmeniarest.security.JwtAuthenticationEntryPoint;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

class RestSecurityConfigTest {


    private RestSecurityConfig restSecurityConfig;

    @MockBean
    private PasswordEncoder passwordEncoder;

    @MockBean
    private UserDetailsService userDetailsService;

    @MockBean
    private JwtAuthenticationEntryPoint authenticationEntryPoint;

    @MockBean
    private JWTAuthenticationTokenFilter jwtAuthenticationTokenFilter;

    @BeforeEach
    void setUp() {
        restSecurityConfig = new RestSecurityConfig(passwordEncoder, userDetailsService, authenticationEntryPoint, jwtAuthenticationTokenFilter);
    }

    @Test
    void securityFilterChain() throws Exception {

    }

}