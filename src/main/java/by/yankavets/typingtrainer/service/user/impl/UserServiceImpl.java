package by.yankavets.typingtrainer.service.user.impl;

import by.yankavets.typingtrainer.constant.ExceptionMessage;
import by.yankavets.typingtrainer.exception.user.UserNotFoundException;
import by.yankavets.typingtrainer.mapper.UserMapper;
import by.yankavets.typingtrainer.model.dto.UserDto;
import by.yankavets.typingtrainer.model.entity.user.User;
import by.yankavets.typingtrainer.repository.EmailConfirmationTokenRepository;
import by.yankavets.typingtrainer.repository.PasswordResetTokenRepository;
import by.yankavets.typingtrainer.repository.UserRepository;
import by.yankavets.typingtrainer.model.entity.token.EmailConfirmationToken;
import by.yankavets.typingtrainer.model.entity.token.PasswordResetToken;
import by.yankavets.typingtrainer.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final EmailConfirmationTokenRepository emailConfirmationTokenRepository;
    private final PasswordResetTokenRepository passwordResetTokenRepository;

    private final UserMapper userMapper;

    @Autowired
    public UserServiceImpl(UserRepository userRepository,
                           EmailConfirmationTokenRepository emailConfirmationTokenRepository,
                           PasswordResetTokenRepository passwordResetTokenRepository,
                           UserMapper userMapper) {
        this.userRepository = userRepository;
        this.emailConfirmationTokenRepository = emailConfirmationTokenRepository;
        this.passwordResetTokenRepository = passwordResetTokenRepository;
        this.userMapper = userMapper;
    }

    public List<UserDto> findAll() {
        return userMapper.toDtoList(userRepository.findAll());
    }

    @Override
    @Transactional
    public User findById(long id) {
        User foundUser = userRepository.findById(id).orElseThrow(
                (() -> new UserNotFoundException(ExceptionMessage.NO_USER_WITH_SUCH_ID)
        ));
        return foundUser;
    }

    @Override
    public boolean isExist(String email) {
        return userRepository.findByEmail(email).isPresent();
    }


    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(
                () -> new UserNotFoundException(ExceptionMessage.NO_USER_WITH_SUCH_EMAIL, email)
        );
    }

    @Override
    @Transactional
    public User save(User registeredUser) {
        return userRepository.save(registeredUser);
    }

    @Override
    public void enableUser(String email) {
        userRepository.enableUser(email);
    }

    @Override
    @Transactional
    public void saveConfirmationToken(EmailConfirmationToken token) {
        emailConfirmationTokenRepository.save(token);
    }

    @Override
    public Optional<EmailConfirmationToken> findEmailToken(String token) {
        return emailConfirmationTokenRepository.findByToken(token);
    }

    @Override
    @Transactional
    public void setConfirmedAt(String token) {
        emailConfirmationTokenRepository.updateConfirmedAt(
                token,
                LocalDateTime.now()
        );
    }

    @Override
    @Transactional
    public void savePasswordResetToken(PasswordResetToken token) {
        passwordResetTokenRepository.save(token);
    }

    @Override
    public Optional<PasswordResetToken> findPasswordResetToken(String token) {
        return passwordResetTokenRepository.findByToken(token);
    }

    @Override
    @Transactional
    public void setResetAt(String token) {
        passwordResetTokenRepository.updateResetAt(
                token,
                LocalDateTime.now()
        );
    }

    @Override
    @Transactional
    public boolean isEnabled(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(
                () -> new UserNotFoundException(ExceptionMessage.NO_USER_WITH_SUCH_EMAIL, email)
        );
        return user.isEnabled();
    }



}
