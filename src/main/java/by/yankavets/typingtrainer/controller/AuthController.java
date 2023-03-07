package by.yankavets.typingtrainer.controller;

import by.yankavets.typingtrainer.exception.user.IncorrectCredentialsException;
import by.yankavets.typingtrainer.model.dto.LoginUserDTO;
import by.yankavets.typingtrainer.model.entity.User;
import by.yankavets.typingtrainer.model.entity.payload.ServerResponse;
import by.yankavets.typingtrainer.model.entity.payload.response.SuccessfulMessage;
import by.yankavets.typingtrainer.service.RegistrationService;
import by.yankavets.typingtrainer.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final RegistrationService registrationService;

    private final UserService userService;

    @Autowired
    public AuthController(RegistrationService registrationService, UserService userService, UserService userService1) {
        this.registrationService = registrationService;
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<ServerResponse> registration(@RequestBody @Valid User user,
                                                   BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new IncorrectCredentialsException(printErrors(bindingResult));
        }
        registrationService.register(user);
        return ResponseEntity.ok(SuccessfulMessage.MESSAGE_USER_CREATED_SUCCESSFUL);
    }

    @PostMapping("/login")
    public User login(Authentication authentication) throws Exception {
        HashMap<String, String> response = new HashMap<>();
        response.put("authStatus", "success");
       return userService.findByEmail(authentication.getName()).orElse(null);
    }

    @RequestMapping("/user")
    public User getUserDetailsAfterLogin(Authentication authentication) {
        Optional<User> user = userService.findByEmail(authentication.getName());
        return user.orElse(null);

    }


    private String printErrors(BindingResult bindingResult) {
        StringBuilder errorMessage = new StringBuilder();

        List<FieldError> errors = bindingResult.getFieldErrors();

        for (FieldError error : errors) {
            errorMessage
                    .append(error.getDefaultMessage())
                    .append("; ");
        }

        errorMessage = new StringBuilder(errorMessage.substring(0, errorMessage.length() - 2));
        return errorMessage.toString();
    }



}
