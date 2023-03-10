package by.yankavets.typingtrainer.security.filter;

import by.yankavets.typingtrainer.constants.SecurityConstant;
import by.yankavets.typingtrainer.exception.ExceptionMessage;
import by.yankavets.typingtrainer.exception.auth.InvalidJWTTokenException;
import by.yankavets.typingtrainer.model.entity.User;
import by.yankavets.typingtrainer.model.entity.payload.ServerResponse;
import by.yankavets.typingtrainer.model.entity.payload.response.AdviceErrorMessage;
import by.yankavets.typingtrainer.security.JwtService;
import by.yankavets.typingtrainer.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.ErrorResponse;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;

@Component
public class JwtTokenValidatorFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserService userService;

    @Autowired
    @Qualifier("handlerExceptionResolver")
    private HandlerExceptionResolver resolver;

    @Autowired
    public JwtTokenValidatorFilter(JwtService jwtService, UserService userService) {
        this.jwtService = jwtService;
        this.userService = userService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String authHeader = request.getHeader(SecurityConstant.AUTH_HEADER);

        if (authHeader != null && !authHeader.isBlank() && authHeader.startsWith(SecurityConstant.BEARER_HEADER)) {
            String jwt = authHeader.substring(7);

            try {

                String userEmail = jwtService.extractUsername(jwt);
                User userFromDB = userService.findByEmail(userEmail);

                if (jwtService.isValidToken(jwt, userFromDB)) {
                    UsernamePasswordAuthenticationToken authToken =
                            new UsernamePasswordAuthenticationToken(
                                    userFromDB.getEmail(),
                                    null,
                                    userFromDB.getAuthorities()
                            );

                    if (SecurityContextHolder.getContext().getAuthentication() == null) {
                        SecurityContextHolder.getContext().setAuthentication(authToken);
                        filterChain.doFilter(request, response);
                    }
                }


            } catch (JwtException e) {
                resolver.resolveException(request, response, null, e);
            }
        }

    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        return request.getServletPath().equals("/auth/login");
    }


}
