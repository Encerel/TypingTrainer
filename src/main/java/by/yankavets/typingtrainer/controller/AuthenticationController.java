package by.yankavets.typingtrainer.controller;

import by.yankavets.typingtrainer.model.dto.SignUpDto;
import by.yankavets.typingtrainer.model.dto.ResetPasswordDto;
import by.yankavets.typingtrainer.model.entity.payload.ServerResponse;
import by.yankavets.typingtrainer.service.security.AuthenticationService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;


@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @Autowired
    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/register")
    public ResponseEntity<ServerResponse> registration(@Valid @RequestBody SignUpDto signUpDTO) {
        return authenticationService.register(signUpDTO);
    }

    @PostMapping("/login")
    public ResponseEntity<ServerResponse> login(Authentication authentication) {
        return authenticationService.authenticate(authentication);
    }

    @GetMapping("/register/confirm")
    public void confirmEmailToken(
            @RequestParam(value = "token")
            String token,
            HttpServletResponse response
    ) throws IOException {
        authenticationService.sendConfirmEmailToken(token);
        response.sendRedirect("http://26.199.222.75:3000/");
    }


    @GetMapping("/password-reset")
    public ResponseEntity<ServerResponse> sendResetCode(
            @RequestParam("email") String email) {
        return authenticationService.sendPasswordResetCode(email);
    }

    @PostMapping("/password-reset")
    public ResponseEntity<ServerResponse> resetPassword(
            @RequestBody
            ResetPasswordDto token) {
        return authenticationService.resetPassword(token);
    }


}
