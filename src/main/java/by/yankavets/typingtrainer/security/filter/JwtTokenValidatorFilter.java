package by.yankavets.typingtrainer.security.filter;

import by.yankavets.typingtrainer.constant.ExceptionMessage;
import by.yankavets.typingtrainer.constant.SecurityConstant;
import by.yankavets.typingtrainer.model.entity.payload.ServerResponse;
import by.yankavets.typingtrainer.model.entity.payload.response.AdviceErrorMessage;
import by.yankavets.typingtrainer.security.impl.JwtServiceImpl;
import by.yankavets.typingtrainer.util.JsonErrorWriter;
import io.jsonwebtoken.JwtException;
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
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;

@Component
public class JwtTokenValidatorFilter extends OncePerRequestFilter {

    private static final String AUTHENTICATION_PATTERN = "^/auth.*";

    private final JwtServiceImpl jwtServiceImpl;

    private final UserDetailsService userDetailsService;

    @Autowired
    public JwtTokenValidatorFilter(JwtServiceImpl jwtServiceImpl,
                                   UserDetailsService userDetailsService) {
        this.jwtServiceImpl = jwtServiceImpl;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String authHeader = request.getHeader(SecurityConstant.AUTH_HEADER);

        if (authHeader != null && !authHeader.isBlank() && authHeader.startsWith(SecurityConstant.BEARER_HEADER)) {
            String jwt = authHeader.substring(7);

            try {

                String userEmail = jwtServiceImpl.extractUsername(jwt);
                UserDetails userDetails = userDetailsService.loadUserByUsername(userEmail);


                if (jwtServiceImpl.isValidToken(jwt, userDetails)) {
                    UsernamePasswordAuthenticationToken authToken =
                            new UsernamePasswordAuthenticationToken(
                                    userDetails,
                                    null,
                                    userDetails.getAuthorities()
                            );

                    if (SecurityContextHolder.getContext().getAuthentication() == null) {
                        SecurityContextHolder.getContext().setAuthentication(authToken);
                        filterChain.doFilter(request, response);

                    }
                }


            } catch (JwtException e) {

                ServerResponse serverResponse = AdviceErrorMessage.builder()
                        .message(ExceptionMessage.INCORRECT_JWT_TOKEN)
                        .status(HttpStatus.UNAUTHORIZED.value())
                        .build();

                String json = JsonErrorWriter.JSON_WRITER.writeValueAsString(serverResponse);

                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
                response.getWriter().write(json);

            }
        }

    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        return request.getServletPath().matches(AUTHENTICATION_PATTERN);
    }


}
