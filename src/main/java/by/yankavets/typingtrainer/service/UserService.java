package by.yankavets.typingtrainer.service;

import by.yankavets.typingtrainer.model.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Optional;

public interface UserService {

    void save(User user);

    void register(User user);

    Optional<User> findByEmail(String email);
}
