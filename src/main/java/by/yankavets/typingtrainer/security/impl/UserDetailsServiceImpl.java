package by.yankavets.typingtrainer.security.impl;

import by.yankavets.typingtrainer.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class UserDetailsServiceImpl implements UserDetailsService {

    private static final String USERNAME_NOT_FOUND_MESSAGE_FORMAT = "User \"%s\" not found!";
    private final UserRepository userRepository;

    @Autowired
    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        return userRepository
                .findByEmail(username)
                .orElseThrow(
                        () ->
                                new UsernameNotFoundException(
                                        String.format(USERNAME_NOT_FOUND_MESSAGE_FORMAT, username)));
    }
}
