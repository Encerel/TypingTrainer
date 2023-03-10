package by.yankavets.typingtrainer.security;

import by.yankavets.typingtrainer.exception.ExceptionMessage;
import by.yankavets.typingtrainer.exception.auth.IncorrectCredentialsException;
import by.yankavets.typingtrainer.model.entity.User;
import by.yankavets.typingtrainer.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class TypingTrainerUsernamePasswordAuthProvider implements AuthenticationProvider {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public TypingTrainerUsernamePasswordAuthProvider(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName().strip();
        String password = authentication.getCredentials().toString().strip();
        Optional<User> userFromDB = userRepository.findByEmail(username);

        if (userFromDB.isEmpty()) {
            throw new IncorrectCredentialsException(ExceptionMessage.WRONG_EMAIL);
        }

        User user = userFromDB.get();

        if (passwordEncoder.matches(password, user.getPassword())) {
            return new UsernamePasswordAuthenticationToken(username, password, user.getAuthorities());
        } else {
            throw new IncorrectCredentialsException(ExceptionMessage.WRONG_PASSWORD);
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
    }
}
