package by.yankavets.typingtrainer.controller;

import by.yankavets.typingtrainer.model.dto.LoginUserDTO;
import by.yankavets.typingtrainer.model.dto.RegisterUserDTO;
import by.yankavets.typingtrainer.model.entity.payload.ServerResponse;
import by.yankavets.typingtrainer.model.entity.payload.response.RegistrationResponse;
import by.yankavets.typingtrainer.model.entity.payload.response.SuccessfulMessage;
import by.yankavets.typingtrainer.service.AuthenticationService;
import by.yankavets.typingtrainer.service.RegistrationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final RegistrationService registrationService;
    private final AuthenticationService authenticationService;
    @Autowired
    private AuthenticationManager authenticationManager;


    @Autowired
    public AuthController(RegistrationService registrationService, AuthenticationService authenticationService) {
        this.registrationService = registrationService;
        this.authenticationService = authenticationService;
    }

    @PostMapping("/register")
    public ResponseEntity<ServerResponse> registration(@RequestBody @Valid RegisterUserDTO registerUserDTO,
                                                       BindingResult bindingResult) {

        registrationService.register(registerUserDTO, bindingResult);

        ServerResponse response = RegistrationResponse.builder()
                .message(SuccessfulMessage.MESSAGE_USER_CREATED_SUCCESSFUL.getMessage())
                .status(HttpStatus.CREATED.value())
                .build();

        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<ServerResponse> login(Authentication authentication) {


        return authenticationService.authenticate(authentication);
    }

    @PostMapping("/activateAccount")
    public ResponseEntity<ServerResponse> getActivationCode(@RequestBody String email) {

        return ResponseEntity.ok(new SuccessfulMessage("All is success", HttpStatus.OK.value()));

    }


}
