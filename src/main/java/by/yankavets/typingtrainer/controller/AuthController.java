package by.yankavets.typingtrainer.controller;

import by.yankavets.typingtrainer.model.dto.RegisterUserDTO;
import by.yankavets.typingtrainer.model.entity.User;
import by.yankavets.typingtrainer.model.entity.payload.ServerResponse;
import by.yankavets.typingtrainer.model.entity.payload.response.AuthenticationResponse;
import by.yankavets.typingtrainer.model.entity.payload.response.SuccessfulMessage;
import by.yankavets.typingtrainer.security.JWTUtil;
import by.yankavets.typingtrainer.service.RegistrationService;
import by.yankavets.typingtrainer.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final RegistrationService registrationService;

    private final UserService userService;
    private final AuthenticationManager authenticationManager;

    private final JWTUtil jwtUtil;
    @Autowired
    public AuthController(RegistrationService registrationService, UserService userService, UserService userService1, AuthenticationManager authenticationManager, JWTUtil jwtUtil) {
        this.registrationService = registrationService;
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/register")
    public ResponseEntity<ServerResponse> registration(@RequestBody @Valid RegisterUserDTO registerUserDTO,
                                                       BindingResult bindingResult) {


        registrationService.register(registerUserDTO, bindingResult);



        String token = jwtUtil.generateToken(registerUserDTO.getEmail());

        ServerResponse response = new AuthenticationResponse(
                token,
                SuccessfulMessage.MESSAGE_USER_CREATED_SUCCESSFUL.getMessage()
        );

        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public User login(Authentication authentication) throws Exception {
       return userService.findByEmail(authentication.getName()).orElse(null);
    }


}
