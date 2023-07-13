package by.yankavets.typingtrainer.service.user.impl;

import by.yankavets.typingtrainer.mapper.UserMapper;
import by.yankavets.typingtrainer.model.entity.user.Role;
import by.yankavets.typingtrainer.model.entity.user.User;
import by.yankavets.typingtrainer.repository.EmailConfirmationTokenRepository;
import by.yankavets.typingtrainer.repository.PasswordResetTokenRepository;
import by.yankavets.typingtrainer.repository.UserRepository;
import by.yankavets.typingtrainer.service.user.UserService;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
@ContextConfiguration(classes = {UserServiceImpl.class})
public class UserServiceImplTest {

    private static final Long TEST_ID = 123L;
    public static final User TEST_USER;
    public static final String TEST_USER_NAME = "Ivan";

    public static final String TEST_USER_EMAIL = "test@email.com";

    public static final String TEST_PASSWORD = "1234";

    public static final Role TEST_USER_ROLE;

    public static final Set<Role> TEST_ROLES;
    private static final List<User> TEST_USER_LIST;

    private List<User> userList;

    static {
        TEST_USER_LIST = new ArrayList<>();
        TEST_USER_ROLE = Role.builder()
                .name("ROLE_USER")
                .build();
        TEST_ROLES = Set.of(TEST_USER_ROLE);
        TEST_USER = User.builder()
                .id(TEST_ID)
                .name(TEST_USER_NAME)
                .email(TEST_USER_EMAIL)
                .password(TEST_PASSWORD)
                .roles(TEST_ROLES)
                .build();
        TEST_USER_LIST.add(TEST_USER);
    }
    @MockBean
    private UserRepository userRepository;
    @MockBean
    private EmailConfirmationTokenRepository emailConfirmationTokenRepository;
    @MockBean
    private PasswordResetTokenRepository passwordResetTokenRepository;
    @Autowired
    private UserService userService;

    @Autowired
    private UserMapper userMapper;

    @Test
    @DisplayName("Add user in database test")
    void addUserInDatabaseTest() {
        userService.save(TEST_USER);
        Optional<User> savedUser = userRepository.findByEmail(TEST_USER.getEmail());
        verify(userRepository, times(1)).save(TEST_USER);
        assertThat(savedUser).isPresent();
        assertThat(savedUser).isEqualTo(TEST_USER);
    }


    @Test
    @DisplayName("Find all user test")
    void findAllUsers() {
        when(userService.findAll()).thenReturn(userMapper.toDtoList(TEST_USER_LIST));
        var actualAll = userRepository.findAll();
        assertIterableEquals(TEST_USER_LIST, actualAll);
        assertFalse(actualAll.isEmpty(), "Should be at least one user");
        verify(userRepository).findAll();
    }


    @Test
    @DisplayName("Find user by email")
    void findUserByEmail() {
        when(userService.findByEmail(TEST_USER_EMAIL)).thenReturn(TEST_USER);
        var actualByEmail = userService.findByEmail(TEST_USER_EMAIL);
        assertThat(TEST_USER).isEqualTo(actualByEmail);
        verify(userRepository).findByEmail(TEST_USER_EMAIL);
    }

    @Test
    @DisplayName("Make user account verified")
    void enableUser() {
        when(userRepository.enableUser(TEST_USER_EMAIL)).thenReturn(1);
        userService.enableUser(TEST_USER_EMAIL);
        verify(userRepository).enableUser(TEST_USER_EMAIL);
    }


}




