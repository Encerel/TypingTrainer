package by.yankavets.typingtrainer.service.user;

import by.yankavets.typingtrainer.model.entity.user.User;

import java.util.Optional;


public interface UserService {
    Optional<User> findByEmail(String email);

    void save(User registeredUser);

    int enableUser(String email);
}
