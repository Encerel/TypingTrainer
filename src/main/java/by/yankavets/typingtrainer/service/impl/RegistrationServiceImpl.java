package by.yankavets.typingtrainer.service.impl;

import by.yankavets.typingtrainer.exception.user.UserIsExistException;
import by.yankavets.typingtrainer.model.entity.User;
import by.yankavets.typingtrainer.repository.UserRepository;
import by.yankavets.typingtrainer.service.RegistrationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class RegistrationServiceImpl implements RegistrationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public RegistrationServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public void register(@Valid User user) {
        Optional<User> userFromDb = userRepository.findByEmail(user.getEmail());

        if (userFromDb.isPresent()) {
            throw new UserIsExistException(user.getEmail());
        }

        String encodedPassword = user.getPassword();
        user.setPassword(passwordEncoder.encode(encodedPassword));
        userRepository.save(user);
    }
}
