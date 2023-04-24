package by.yankavets.typingtrainer.controller;

import by.yankavets.typingtrainer.model.dto.RegisterUserDTO;
import by.yankavets.typingtrainer.model.dto.ResetPasswordDTO;
import by.yankavets.typingtrainer.model.entity.payload.ServerResponse;
import by.yankavets.typingtrainer.service.security.AuthenticationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationService authenticationService;

    @Autowired
    public AuthController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/register")
    public ResponseEntity<ServerResponse> registration(@RequestBody @Valid RegisterUserDTO registerUserDTO,
                                                       BindingResult bindingResult) {


        return authenticationService.register(registerUserDTO, bindingResult);
    }

    @PostMapping("/login")
    public ResponseEntity<ServerResponse> login(Authentication authentication) {


        return authenticationService.authenticate(authentication);
    }

    @GetMapping("/register/confirm")
    public ResponseEntity<ServerResponse> confirmEmailToken(
            @RequestParam(value = "token")
            String token
    ) {
        return authenticationService.confirmEmailToken(token);
    }


    @GetMapping("/password-reset")
    public ResponseEntity<ServerResponse> sendResetCode(@RequestParam("email") String email) {
        return authenticationService.sendPasswordResetCode(email);
    }

    @PostMapping("/password-reset")
    public ResponseEntity<ServerResponse> resetPassword(
            @RequestBody
            ResetPasswordDTO resetPasswordDTO) {
        return authenticationService.resetPassword(resetPasswordDTO);
    }


}
