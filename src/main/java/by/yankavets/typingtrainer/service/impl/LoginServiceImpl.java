package by.yankavets.typingtrainer.service.impl;

import by.yankavets.typingtrainer.exception.user.IncorrectPasswordException;
import by.yankavets.typingtrainer.model.dto.LoginUserDTO;
import by.yankavets.typingtrainer.model.entity.User;
import by.yankavets.typingtrainer.repository.UserRepository;
import by.yankavets.typingtrainer.service.LoginService;
import by.yankavets.typingtrainer.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class LoginServiceImpl implements LoginService {

    private final UserRepository userRepository;

    @Autowired
    public LoginServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void login(LoginUserDTO userDTO, String userFromDBPassword) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(userDTO.getPassword());
        if (!encodedPassword.equals(userFromDBPassword)) {
            throw new IncorrectPasswordException("Password is incorrect");
        }

    }
}
