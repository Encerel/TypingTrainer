package by.yankavets.typingtrainer.service.email;

import by.yankavets.typingtrainer.service.email.token.EmailConfirmationToken;

import java.util.Optional;

public interface EmailConfirmationTokenService {

    void saveConfirmationToken(EmailConfirmationToken token);
    Optional<EmailConfirmationToken> findToken(String token);
    void setConfirmedAt(String token);

}
