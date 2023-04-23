package by.yankavets.typingtrainer.service.security.impl;

import by.yankavets.typingtrainer.constant.ExceptionMessage;
import by.yankavets.typingtrainer.constant.SuccessfulMessage;
import by.yankavets.typingtrainer.exception.auth.*;
import by.yankavets.typingtrainer.model.dto.RegisterUserDTO;
import by.yankavets.typingtrainer.model.dto.UserDTO;
import by.yankavets.typingtrainer.model.entity.user.RoleName;
import by.yankavets.typingtrainer.model.entity.user.User;
import by.yankavets.typingtrainer.model.entity.payload.ServerResponse;
import by.yankavets.typingtrainer.model.entity.payload.response.AuthenticationResponse;
import by.yankavets.typingtrainer.model.entity.payload.response.RegistrationResponse;
import by.yankavets.typingtrainer.model.entity.payload.response.MessageServerResponse;
import by.yankavets.typingtrainer.repository.RoleRepository;
import by.yankavets.typingtrainer.security.impl.JwtServiceImpl;
import by.yankavets.typingtrainer.service.email.EmailService;
import by.yankavets.typingtrainer.service.user.UserService;
import by.yankavets.typingtrainer.service.security.AuthenticationService;
import by.yankavets.typingtrainer.service.email.EmailConfirmationTokenService;
import by.yankavets.typingtrainer.service.email.token.EmailConfirmationToken;
import by.yankavets.typingtrainer.util.DTOErrorPrinter;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
public class AuthenticationServiceImpl implements AuthenticationService {

    private final ModelMapper modelMapper;
    private final JwtServiceImpl jwtServiceImpl;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final EmailConfirmationTokenService emailConfirmationTokenService;
    private final EmailService emailService;

    private static final String EMAIL_LINK = "http://26.189.24.33:8080/auth/register/confirm?token=";


    @Autowired
    public AuthenticationServiceImpl(ModelMapper modelMapper,
                                     JwtServiceImpl jwtServiceImpl,
                                     UserService userService, PasswordEncoder passwordEncoder,
                                     RoleRepository roleRepository,
                                     EmailConfirmationTokenService emailConfirmationTokenService,
                                     EmailService emailService) {
        this.userService = userService;
        this.modelMapper = modelMapper;
        this.jwtServiceImpl = jwtServiceImpl;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
        this.emailConfirmationTokenService = emailConfirmationTokenService;
        this.emailService = emailService;
    }

    @Override
    @Transactional
    public ResponseEntity<ServerResponse> register(@Valid RegisterUserDTO registerUserDTO,
                                                   BindingResult bindingResult) {
        Optional<User> userFromDb = userService.findByEmail(registerUserDTO.getEmail());

        if (bindingResult.hasErrors()) {
            throw new IncorrectCredentialsException(DTOErrorPrinter.printErrors(bindingResult));
        }

        if (userFromDb.isPresent()) {
            throw new UserIsAlreadyExistException(registerUserDTO.getEmail());
        }

        User registeredUser = new User();
        String encodedPassword = passwordEncoder.encode(registerUserDTO.getPassword());
        registeredUser.setName(registerUserDTO.getName());
        registeredUser.setEmail(registerUserDTO.getEmail());
        registeredUser.setPassword(encodedPassword);

        registeredUser.setRoles(Set.of(roleRepository.findByName(RoleName.ROLE_USER.name())));
        registeredUser.setCreatedAt(LocalDate.now());
        userService.save(registeredUser);

        String token = UUID.randomUUID().toString();

        EmailConfirmationToken emailConfirmationToken = EmailConfirmationToken.builder()
                .token(token)
                .createdAt(LocalDateTime.now())
                .expiresAt(LocalDateTime.now().plusDays(1))
                .user(registeredUser)
                .build();

       emailConfirmationTokenService.saveConfirmationToken(emailConfirmationToken);

        emailService.send(
                registeredUser.getEmail(),
                emailService.buildLetter(registeredUser.getName(), EMAIL_LINK + emailConfirmationToken.getToken())
        );

        ServerResponse response = RegistrationResponse.builder()
                .message(SuccessfulMessage.USER_CREATED_SUCCESSFULLY)
                .status(HttpStatus.CREATED.value())
                .confirmationToken(token)
                .build();

        return ResponseEntity.ok(response);
    }



    @Override
    public ResponseEntity<ServerResponse> authenticate(Authentication authentication) {

        ServerResponse serverResponse;

        if (authentication == null) {
            serverResponse = AuthenticationResponse
                    .builder()
                    .status(HttpStatus.UNAUTHORIZED.value())
                    .build();
            return ResponseEntity.ok(serverResponse);
        }

        Optional<User> userFromDB = userService.findByEmail(authentication.getName());

        if (userFromDB.isEmpty()) {
            throw new UsernameNotFoundException(ExceptionMessage.WRONG_EMAIL);
        }

        User foundUser = userFromDB.get();

        serverResponse = AuthenticationResponse
                .builder()
                .userDTO(modelMapper.map(foundUser, UserDTO.class))
                .status(HttpStatus.OK.value())
                .jwtToken(jwtServiceImpl.generateToken(foundUser))
                .build();


        return ResponseEntity.ok(serverResponse);
    }

    @Override
    @Transactional
    public ResponseEntity<ServerResponse> confirmEmailToken(String token) {

        Optional<EmailConfirmationToken> confirmationTokenOptional = emailConfirmationTokenService.findToken(token);

        if (confirmationTokenOptional.isEmpty()) {
            throw new InvalidEmailTokenException(ExceptionMessage.TOKEN_NOT_FOUND);
        }

        EmailConfirmationToken confirmationToken = confirmationTokenOptional.get();

        if (confirmationToken.getConfirmedAt() != null) {
            throw new AccountIsAlreadyActivatedException(ExceptionMessage.ACCOUNT_IS_ALREADY_CONFIRMED);
        }

        LocalDateTime expiredAt = confirmationToken.getExpiresAt();

        if (expiredAt.isBefore(LocalDateTime.now())) {
            throw new EmailTokenIsExpiredException(ExceptionMessage.CONFIRMATION_TOKEN_EXPIRED);
        }

        emailConfirmationTokenService.setConfirmedAt(token);
        userService.enableUser(
                confirmationToken.getUser().getEmail()
        );

        ServerResponse response = MessageServerResponse.builder()
                .status(HttpStatus.OK.value())
                .message(SuccessfulMessage.ACCOUNT_CONFIRMED)
                .build();

        return ResponseEntity.ok(response);
    }
}
