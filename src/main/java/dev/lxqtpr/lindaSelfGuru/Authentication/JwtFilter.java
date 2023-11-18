package dev.lxqtpr.lindaSelfGuru.Authentication;

import jakarta.servlet.ServletException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;

@Service
public class JwtFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final CustomUserDetailsService customUserDetailsService;
    private final HandlerExceptionResolver resolver;
    private static final String AUTHORIZATION = "Authorization";
    private final RequestMatcher ignoredPaths = new AntPathRequestMatcher("/auth/refresh");

    @Autowired
    public JwtFilter(
            JwtService jwtService,
            CustomUserDetailsService customUserDetailsService,
            @Qualifier("handlerExceptionResolver") HandlerExceptionResolver resolver
    ) {
        this.jwtService = jwtService;
        this.customUserDetailsService = customUserDetailsService;
        this.resolver = resolver;
    }
    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        if (this.ignoredPaths.matches(request)) {
            filterChain.doFilter(request, response);
            return;
        }
        try {
            String accessToken = getTokenFromRequest(request);
            if (accessToken != null && jwtService.validateAccessToken(accessToken)) {
                var userEmail = jwtService.getUserEmailFromAccessClaims(accessToken);
                var userDetails = customUserDetailsService.loadUserByUsername(userEmail);
                var authentication = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );
                authentication.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
            filterChain.doFilter(request, response);
        }
        catch (Exception e) {
            resolver.resolveException(request, response, null, e);
        }
    }
    private String getTokenFromRequest(HttpServletRequest request) {
        final String bearer = request.getHeader(AUTHORIZATION);
        if (StringUtils.hasText(bearer) && bearer.startsWith("Bearer ")) {
            return bearer.substring(7);
        }
        return null;
    }
}
