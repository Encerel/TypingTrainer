package by.yankavets.typingtrainer.controller;

import by.yankavets.typingtrainer.model.entity.User;
import by.yankavets.typingtrainer.service.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final RegistrationService registrationService;

    @Autowired
    public AuthController(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    @PostMapping("/register")
    public ResponseEntity<HttpStatus> registration(@RequestBody User user) {

        registrationService.register(user);
        return ResponseEntity.ok(HttpStatus.OK);
    }


}
