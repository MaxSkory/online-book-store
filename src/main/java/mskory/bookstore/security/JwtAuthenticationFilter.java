package mskory.bookstore.security;

import jakarta.annotation.PostConstruct;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import lombok.RequiredArgsConstructor;
import mskory.bookstore.config.SecurityConfig;
import mskory.bookstore.exception.JwtAuthenticationException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private static final String HEADER_NAME = "Authorization";
    private static final String TOKEN_PREFIX = "Bearer ";
    private final UserDetailsService customUserDetailsService;
    private final JwtUtil jwtUtil;
    private List<AntPathRequestMatcher> excludedMatchers;

    @PostConstruct
    protected void init() {
        excludedMatchers = Arrays.stream(SecurityConfig.getPermittedPaths())
                .map(AntPathRequestMatcher::new)
                .toList();
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {
        if (shouldNotFilter(request)) {
            filterChain.doFilter(request, response);
            return;
        }
        String token = request.getHeader(HEADER_NAME);
        if (StringUtils.hasText(token) && token.startsWith(TOKEN_PREFIX)) {
            try {
                Authentication authentication = createAuthentication(parseToken(token));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } catch (Exception e) {
                throw new JwtAuthenticationException("JWT authentication failed!", e);
            }
        }
        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        return excludedMatchers.stream()
                .anyMatch(matcher -> matcher.matches(request));
    }

    private Authentication createAuthentication(String token) {
        String subject = jwtUtil.getSubject(token);
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(subject);
        return new UsernamePasswordAuthenticationToken(subject, null, userDetails.getAuthorities());
    }

    private String parseToken(String token) {
        return token.substring(TOKEN_PREFIX.length());
    }
}
