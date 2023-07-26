package com.example.tourarmeniarest.filter;


import com.example.tourarmeniarest.security.CurrentUserDetailServiceImpl;
import com.example.tourarmeniarest.util.JwtTokenUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
@Slf4j
public class JWTAuthenticationTokenFilter extends OncePerRequestFilter {

    private final JwtTokenUtil tokenUtil;

    private final CurrentUserDetailServiceImpl userDetailsService;

//Custom implementation of the doFilterInternal method for JWT authentication.
// This method intercepts incoming HTTP requests and checks for a valid JWT token in the "Authorization" header.
// If a valid token is found, it verifies the token's authenticity and sets the authenticated user in the SecurityContextHolder.
// The request is then allowed to proceed to the intended endpoint.
    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {

        String requestHeader = httpServletRequest.getHeader("Authorization");

        String username = null;
        String authToken = null;
        if (requestHeader != null && requestHeader.startsWith("Bearer ")) {
            authToken = requestHeader.substring(7);
            try {
                username = tokenUtil.getUsernameFromToken(authToken);
            } catch (Exception e) {
                log.error("Error while parsing the JWT token: {}", e.getMessage());
                e.printStackTrace();
            }
        }
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

            if (tokenUtil.validateToken(authToken, userDetails.getUsername())) {
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }

        filterChain.doFilter(httpServletRequest, httpServletResponse);

    }
}