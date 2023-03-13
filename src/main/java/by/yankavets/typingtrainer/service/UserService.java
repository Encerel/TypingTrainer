package by.yankavets.typingtrainer.service;

import by.yankavets.typingtrainer.model.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;


public interface UserService {
    User findByEmail(String email);
}
