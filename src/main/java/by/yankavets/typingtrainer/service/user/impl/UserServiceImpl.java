package by.yankavets.typingtrainer.service.user.impl;

import by.yankavets.typingtrainer.model.entity.user.User;
import by.yankavets.typingtrainer.repository.EmailConfirmationTokenRepository;
import by.yankavets.typingtrainer.repository.PasswordResetTokenRepository;
import by.yankavets.typingtrainer.repository.UserRepository;
import by.yankavets.typingtrainer.service.email.token.EmailConfirmationToken;
import by.yankavets.typingtrainer.service.email.token.PasswordResetToken;
import by.yankavets.typingtrainer.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final EmailConfirmationTokenRepository emailConfirmationTokenRepository;
    private final PasswordResetTokenRepository passwordResetTokenRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, EmailConfirmationTokenRepository emailConfirmationTokenRepository, PasswordResetTokenRepository passwordResetTokenRepository) {
        this.userRepository = userRepository;
        this.emailConfirmationTokenRepository = emailConfirmationTokenRepository;
        this.passwordResetTokenRepository = passwordResetTokenRepository;
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


}
