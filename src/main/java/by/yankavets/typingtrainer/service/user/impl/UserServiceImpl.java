package by.yankavets.typingtrainer.service.user.impl;

import by.yankavets.typingtrainer.model.entity.user.User;
import by.yankavets.typingtrainer.repository.UserRepository;
import by.yankavets.typingtrainer.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }



    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public void save(User registeredUser) {
        userRepository.save(registeredUser);
    }

    @Override
    public int enableUser(String email) {
        return userRepository.enableUser(email);
    }
}
