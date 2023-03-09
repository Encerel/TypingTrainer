package by.yankavets.typingtrainer.controller;

import by.yankavets.typingtrainer.model.entity.User;
import by.yankavets.typingtrainer.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/profile")
public class ProfileController {

    private final UserService userService;

    @Autowired
    public ProfileController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public User profile(Authentication authentication) {
        return userService.findByEmail(authentication.getName()).orElse(null);
    }

}
