package by.yankavets.typingtrainer.service.security.impl;

import by.yankavets.typingtrainer.constant.ExceptionMessage;
import by.yankavets.typingtrainer.constant.Message;
import by.yankavets.typingtrainer.exception.auth.*;
import by.yankavets.typingtrainer.model.dto.RegisterUserDTO;
import by.yankavets.typingtrainer.model.dto.ResetPasswordDTO;
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
import by.yankavets.typingtrainer.service.email.impl.EmailConfirmationService;
import by.yankavets.typingtrainer.service.email.impl.PasswordResetService;
import by.yankavets.typingtrainer.service.email.token.PasswordResetToken;
import by.yankavets.typingtrainer.service.user.UserService;
import by.yankavets.typingtrainer.service.security.AuthenticationService;
import by.yankavets.typingtrainer.service.email.token.EmailConfirmationToken;
import by.yankavets.typingtrainer.util.DTOErrorPrinter;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.BadCredentialsException;
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


    private final JavaMailSender mailSender;

    private static final String EMAIL_LINK = "http://26.189.24.33:8080/auth/register/confirm?token=";


    @Autowired
    public AuthenticationServiceImpl(ModelMapper modelMapper,
                                     JwtServiceImpl jwtServiceImpl,
                                     UserService userService, PasswordEncoder passwordEncoder,
                                     RoleRepository roleRepository,
                                     JavaMailSender mailSender) {
        this.userService = userService;
        this.modelMapper = modelMapper;
        this.jwtServiceImpl = jwtServiceImpl;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
        this.mailSender = mailSender;
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

        userService.saveConfirmationToken(emailConfirmationToken);
        EmailService emailService = new EmailConfirmationService(mailSender);
        emailService.send(
                registeredUser.getEmail(),
                emailService.composeLetter(registeredUser.getName(), EMAIL_LINK + emailConfirmationToken.getToken())
        );

        ServerResponse response = RegistrationResponse.builder()
                .message(Message.USER_CREATED_SUCCESSFULLY)
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

        Optional<EmailConfirmationToken> confirmationTokenOptional = userService.findEmailToken(token);

        if (confirmationTokenOptional.isEmpty()) {
            throw new InvalidTokenException(ExceptionMessage.WRONG_TOKEN);
        }

        EmailConfirmationToken confirmationToken = confirmationTokenOptional.get();

        if (confirmationToken.getConfirmedAt() != null) {
            throw new AccountIsAlreadyActivatedException(ExceptionMessage.ACCOUNT_IS_ALREADY_CONFIRMED);
        }

        LocalDateTime expiredAt = confirmationToken.getExpiresAt();

        if (expiredAt.isBefore(LocalDateTime.now())) {
            throw new TokenIsExpiredException(ExceptionMessage.CONFIRMATION_TOKEN_EXPIRED);
        }

        userService.setConfirmedAt(token);
        userService.enableUser(
                confirmationToken.getUser().getEmail()
        );

        ServerResponse response = MessageServerResponse.builder()
                .status(HttpStatus.OK.value())
                .message(Message.ACCOUNT_CONFIRMED)
                .build();

        return ResponseEntity.ok(response);
    }

    @Override
    @Transactional
    public ResponseEntity<ServerResponse> sendPasswordResetCode(String email) {

        Optional<User> optionalUser = userService.findByEmail(email);

        if (optionalUser.isEmpty()) {
            throw new UsernameNotFoundException("User with such email does not exist!");
        }
        User user = optionalUser.get();

        EmailService emailService = new PasswordResetService(mailSender);

        String token = UUID.randomUUID().toString();

        emailService.send(email, emailService.composeLetter(
                user.getName(),
                token
        ));


        PasswordResetToken resetToken = PasswordResetToken.builder()
                .token(token)
                .createdAt(LocalDateTime.now())
                .expiresAt(LocalDateTime.now().plusMinutes(15))
                .user(user)
                .build();

        userService.savePasswordResetToken(resetToken);

        ServerResponse response = MessageServerResponse.builder()
                .status(HttpStatus.OK.value())
                .message(Message.RESET_PASSWORD_MESSAGE)
                .build();

        return ResponseEntity.ok(response);
    }

    @Override
    @Transactional
    public ResponseEntity<ServerResponse> resetPassword(ResetPasswordDTO resetPasswordDTO) {

        if (!resetPasswordDTO.getPassword().equals(resetPasswordDTO.getConfirmedPassword())) {
            throw new BadCredentialsException(ExceptionMessage.PASSWORDS_MISMATCH);
        }

        Optional<PasswordResetToken> optionalPasswordResetToken = userService.findPasswordResetToken(resetPasswordDTO.getToken());

        if (optionalPasswordResetToken.isEmpty()) {
            throw new InvalidTokenException(ExceptionMessage.WRONG_TOKEN);
        }
        PasswordResetToken passwordResetToken = optionalPasswordResetToken.get();


        LocalDateTime expiredAt = passwordResetToken.getExpiresAt();

        if (expiredAt.isBefore(LocalDateTime.now())) {
            throw new TokenIsExpiredException(ExceptionMessage.PASSWORD_RESET_TOKEN_EXPIRED);
        }

        userService.setResetAt(resetPasswordDTO.getToken());

        User user = passwordResetToken.getUser();
        user.setPassword(passwordEncoder.encode(resetPasswordDTO.getPassword()));
        userService.save(user);

        ServerResponse response = MessageServerResponse.builder()
                .status(HttpStatus.OK.value())
                .message(Message.PASSWORD_WAS_CHANGED)
                .build();

        return ResponseEntity.ok(response);
    }
}
