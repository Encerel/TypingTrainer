package by.yankavets.typingtrainer.service.email.impl;

import by.yankavets.typingtrainer.repository.EmailConfirmationTokenRepository;
import by.yankavets.typingtrainer.service.email.EmailConfirmationTokenService;
import by.yankavets.typingtrainer.service.email.token.EmailConfirmationToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
//@Transactional(readOnly = true)
public class EmailConfirmationTokenServiceImpl implements EmailConfirmationTokenService {

    private final EmailConfirmationTokenRepository tokenRepository;

    @Autowired
    public EmailConfirmationTokenServiceImpl(EmailConfirmationTokenRepository tokenRepository) {
        this.tokenRepository = tokenRepository;
    }

    @Override
    @Transactional
    public void saveConfirmationToken(EmailConfirmationToken token) {
        tokenRepository.save(token);
    }

    @Override
    public Optional<EmailConfirmationToken> findToken(String token) {
        return tokenRepository.findByToken(token);
    }

    @Override
    @Transactional
    public void setConfirmedAt(String token) {
        tokenRepository.updateConfirmedAt(
                token,
                LocalDateTime.now()
        );
    }
}
