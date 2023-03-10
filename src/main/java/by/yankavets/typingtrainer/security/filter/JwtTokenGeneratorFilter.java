package by.yankavets.typingtrainer.security.filter;

import by.yankavets.typingtrainer.constants.SecurityConstant;
import by.yankavets.typingtrainer.model.entity.User;
import by.yankavets.typingtrainer.security.JwtService;
import by.yankavets.typingtrainer.service.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtTokenGeneratorFilter extends OncePerRequestFilter {


    private final JwtService jwtService;
    private final UserService userService;

    @Autowired
    public JwtTokenGeneratorFilter(JwtService jwtService, UserService userService) {
        this.jwtService = jwtService;
        this.userService = userService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null) {
            User userFromDB = userService.findByEmail(authentication.getName());
            response.setHeader(SecurityConstant.AUTH_HEADER, jwtService.generateToken(userFromDB));
        }

        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        return !request.getServletPath().equals("/auth/login");
    }
}
