package by.yankavets.typingtrainer.service;

import by.yankavets.typingtrainer.model.entity.User;
import by.yankavets.typingtrainer.repository.UserRepository;
import by.yankavets.typingtrainer.security.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<User> userFromDB = userRepository.findUsersByEmail(username);

        if (userFromDB.isEmpty()) {
            throw new UsernameNotFoundException(String.format("User with %s not found", username));
        }


        return new UserDetailsImpl(userFromDB.get());
    }
}
