package by.yankavets.typingtrainer.service.user;

import by.yankavets.typingtrainer.model.dto.UserDto;
import by.yankavets.typingtrainer.model.entity.user.User;
import by.yankavets.typingtrainer.model.entity.token.EmailConfirmationToken;
import by.yankavets.typingtrainer.model.entity.token.PasswordResetToken;

import java.util.List;
import java.util.Optional;


public interface UserService {

    List<UserDto> findAll();
    User findByEmail(String email);

    User save(User registeredUser);

    void enableUser(String email);

    void saveConfirmationToken(EmailConfirmationToken token);
    Optional<EmailConfirmationToken> findEmailToken(String token);
    void setConfirmedAt(String token);

    void savePasswordResetToken(PasswordResetToken token);

    Optional<PasswordResetToken> findPasswordResetToken(String token);

    void setResetAt(String token);

    boolean isEnabled(String email);

    User findById(long id);

    boolean isExist(String email);
}
