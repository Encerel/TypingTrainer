package by.yankavets.typingtrainer.service.impl;

import by.yankavets.typingtrainer.exception.user.IncorrectCredentialsException;
import by.yankavets.typingtrainer.exception.user.UserIsExistException;
import by.yankavets.typingtrainer.model.dto.RegisterUserDTO;
import by.yankavets.typingtrainer.model.entity.User;
import by.yankavets.typingtrainer.repository.RoleRepository;
import by.yankavets.typingtrainer.repository.UserRepository;
import by.yankavets.typingtrainer.service.RegistrationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.time.LocalDate;
import java.util.*;

@Service
@Transactional(readOnly = true)
public class RegistrationServiceImpl implements RegistrationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    @Autowired
    public RegistrationServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }

    @Override
    @Transactional
    public void register(@Valid RegisterUserDTO registerUserDTO, BindingResult bindingResult) {
        Optional<User> userFromDb = userRepository.findByEmail(registerUserDTO.getEmail());

        if (bindingResult.hasErrors()) {
            throw new IncorrectCredentialsException(printErrors(bindingResult));
        }

        if (userFromDb.isPresent()) {
            throw new UserIsExistException(registerUserDTO.getEmail());
        }

        User registeredUser = new User();
        String encodedPassword = passwordEncoder.encode(registerUserDTO.getPassword());
        registeredUser.setName(registerUserDTO.getName());
        registeredUser.setEmail(registerUserDTO.getEmail());
        registeredUser.setPassword(encodedPassword);
        registeredUser.setRoles(Set.of(roleRepository.findByName("ROLE_USER")));
        registeredUser.setCreatedAt(LocalDate.now());
        userRepository.save(registeredUser);
    }

    private String printErrors(BindingResult bindingResult) {
        StringBuilder errorMessage = new StringBuilder();

        List<FieldError> errors = bindingResult.getFieldErrors();

        for (FieldError error : errors) {
            errorMessage
                    .append(error.getDefaultMessage())
                    .append("; ");
        }

        errorMessage = new StringBuilder(errorMessage.substring(0, errorMessage.length() - 2));
        return errorMessage.toString();
    }
}
