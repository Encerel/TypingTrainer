package by.yankavets.typingtrainer.service.security.impl;

import by.yankavets.typingtrainer.constant.ExceptionMessage;
import by.yankavets.typingtrainer.constant.Message;
import by.yankavets.typingtrainer.exception.auth.*;
import by.yankavets.typingtrainer.exception.user.UserNotFoundException;
import by.yankavets.typingtrainer.mapper.UserMapper;
import by.yankavets.typingtrainer.model.dto.SignUpDto;
import by.yankavets.typingtrainer.model.dto.ResetPasswordDto;
import by.yankavets.typingtrainer.model.entity.training.Course;
import by.yankavets.typingtrainer.model.entity.training.Exercise;
import by.yankavets.typingtrainer.model.entity.training.Lesson;
import by.yankavets.typingtrainer.model.entity.user.RoleName;
import by.yankavets.typingtrainer.model.entity.user.User;
import by.yankavets.typingtrainer.model.entity.payload.ServerResponse;
import by.yankavets.typingtrainer.model.entity.payload.response.AuthenticationResponse;
import by.yankavets.typingtrainer.model.entity.payload.response.MessageServerResponse;
import by.yankavets.typingtrainer.repository.RoleRepository;
import by.yankavets.typingtrainer.security.impl.JwtServiceImpl;
import by.yankavets.typingtrainer.service.email.EmailService;
import by.yankavets.typingtrainer.service.email.impl.EmailConfirmationService;
import by.yankavets.typingtrainer.service.email.impl.PasswordResetService;
import by.yankavets.typingtrainer.model.entity.token.PasswordResetToken;
import by.yankavets.typingtrainer.service.training.CourseService;
import by.yankavets.typingtrainer.service.training.ExerciseService;
import by.yankavets.typingtrainer.service.training.LessonService;
import by.yankavets.typingtrainer.service.user.UserService;
import by.yankavets.typingtrainer.service.security.AuthenticationService;
import by.yankavets.typingtrainer.model.entity.token.EmailConfirmationToken;
import by.yankavets.typingtrainer.validator.UserValidator;
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

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
public class AuthenticationServiceImpl implements AuthenticationService {

    private final JwtServiceImpl jwtServiceImpl;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final JavaMailSender mailSender;
    private final CourseService courseService;

    private final UserMapper userMapper;
    private final LessonService lessonService;
    private final ExerciseService exerciseService;
    private static final String EMAIL_LINK = "http://26.189.24.33:8080/auth/register/confirm?token=";
    private static final String EMAIL_REGEX = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";


    @Autowired
    public AuthenticationServiceImpl(JwtServiceImpl jwtServiceImpl,
                                     UserService userService, PasswordEncoder passwordEncoder,
                                     RoleRepository roleRepository,
                                     JavaMailSender mailSender, CourseService courseService,
                                     UserMapper userMapper, LessonService lessonService, ExerciseService exerciseService) {
        this.userService = userService;
        this.jwtServiceImpl = jwtServiceImpl;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
        this.mailSender = mailSender;
        this.courseService = courseService;
        this.userMapper = userMapper;
        this.lessonService = lessonService;
        this.exerciseService = exerciseService;
    }

    @Override
    @Transactional
    public ResponseEntity<ServerResponse> register(SignUpDto signUpDTO) {

        if (userService.isExist(signUpDTO.getEmail())) {
            throw new UserIsAlreadyExistException(signUpDTO.getEmail());
        }

        UserValidator.validate(signUpDTO);

        User registeredUser = buildUserEntity(signUpDTO);
        User savedUser = userService.save(registeredUser);
        exerciseService.enableFirstExercises(savedUser.getId());

        EmailConfirmationToken emailConfirmationToken = buildEmailToken(registeredUser);

        userService.saveConfirmationToken(emailConfirmationToken);
        EmailService emailService = new EmailConfirmationService(mailSender);
        emailService.send(
                registeredUser.getEmail(),
                emailService.composeLetter(registeredUser.getName(), EMAIL_LINK + emailConfirmationToken.getToken())
        );

        ServerResponse response = buildMessageResponse(
                HttpStatus.CREATED.value(),
                Message.USER_CREATED_SUCCESSFULLY);

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

        User userFromDB = userService.findByEmail(authentication.getName());

        serverResponse = AuthenticationResponse
                .builder()
                .userDTO(userMapper.toDto(userFromDB))
                .status(HttpStatus.OK.value())
                .jwtToken(jwtServiceImpl.generateToken(userFromDB))
                .build();


        return ResponseEntity.ok(serverResponse);
    }

    @Override
    @Transactional
    public ResponseEntity<ServerResponse> sendConfirmEmailToken(String token) {

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



        ServerResponse response = buildMessageResponse(
                HttpStatus.OK.value(),
                Message.ACCOUNT_CONFIRMED
        );

        return ResponseEntity.ok(response);
    }

    @Override
    @Transactional
    public ResponseEntity<ServerResponse> sendPasswordResetCode(String email) {

        if (!email.matches(EMAIL_REGEX)) {
            throw new IncorrectCredentialsException(ExceptionMessage.WRONG_EMAIL);
        }

        User userFromDB = userService.findByEmail(email);

        EmailService emailService = new PasswordResetService(mailSender);

        String token = UUID.randomUUID().toString();

        emailService.send(email, emailService.composeLetter(
                userFromDB.getName(),
                token
        ));

        PasswordResetToken resetToken = PasswordResetToken.builder()
                .token(token)
                .createdAt(LocalDateTime.now())
                .expiresAt(LocalDateTime.now().plusMinutes(15))
                .user(userFromDB)
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
    public ResponseEntity<ServerResponse> resetPassword(ResetPasswordDto resetPasswordDTO) {

        if (!resetPasswordDTO.getPassword().equals(resetPasswordDTO.getConfirmedPassword())) {
            throw new BadCredentialsException(ExceptionMessage.PASSWORDS_MISMATCH);
        }

        Optional<PasswordResetToken> optionalPasswordResetToken = userService.findPasswordResetToken(resetPasswordDTO.getToken());

        if (optionalPasswordResetToken.isEmpty()) {
            throw new InvalidTokenException(ExceptionMessage.WRONG_TOKEN);
        }
        PasswordResetToken passwordResetToken = optionalPasswordResetToken.get();

        if (passwordResetToken.getResetAt() != null) {
            throw new TokenIsExpiredException(ExceptionMessage.PASSWORD_RESET_TOKEN_WAS_USED);
        }

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
                .message(Message.PASSWORD_HAS_CHANGED)
                .build();

        return ResponseEntity.ok(response);
    }

    private User buildUserEntity(SignUpDto signUpDTO) {
        List<Course> courses = courseService.findAll();
        List<Lesson> lessons = lessonService.findAll();
        List<Exercise> exercises = exerciseService.findAll();
        User registeredUser = new User();
        String encodedPassword = passwordEncoder.encode(signUpDTO.getPassword());
        registeredUser.setName(signUpDTO.getName());
        registeredUser.setEmail(signUpDTO.getEmail());
        registeredUser.setPassword(encodedPassword);
        registeredUser.setRoles(Set.of(roleRepository.findByName(RoleName.ROLE_USER.name())));
        registeredUser.setCreatedAt(LocalDate.now());
        registeredUser.setCourses(courses);
        registeredUser.setLessons(lessons);
        registeredUser.setExercises(exercises);
        return registeredUser;

    }

    private EmailConfirmationToken buildEmailToken(User registeredUser) {
        String token = UUID.randomUUID().toString();
        return EmailConfirmationToken.builder()
                .token(token)
                .createdAt(LocalDateTime.now())
                .expiresAt(LocalDateTime.now().plusDays(1))
                .user(registeredUser)
                .build();
    }

    private MessageServerResponse buildMessageResponse(int status, String message) {
        return MessageServerResponse.builder()
                .message(message)
                .status(status)
                .build();
    }




}
