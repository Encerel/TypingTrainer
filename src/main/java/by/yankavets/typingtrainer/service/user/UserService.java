package by.yankavets.typingtrainer.service.user;

import by.yankavets.typingtrainer.model.entity.user.User;
import by.yankavets.typingtrainer.service.email.token.EmailConfirmationToken;
import by.yankavets.typingtrainer.service.email.token.PasswordResetToken;

import java.util.Optional;


public interface UserService {
    Optional<User> findByEmail(String email);

    void save(User registeredUser);

    int enableUser(String email);

    void saveConfirmationToken(EmailConfirmationToken token);
    Optional<EmailConfirmationToken> findEmailToken(String token);
    void setConfirmedAt(String token);

    void savePasswordResetToken(PasswordResetToken token);

    Optional<PasswordResetToken> findPasswordResetToken(String token);

    void setResetAt(String token);
}
