package by.yankavets.typingtrainer.controller;

import by.yankavets.typingtrainer.constant.ExceptionMessage;
import by.yankavets.typingtrainer.exception.user.UserNotFoundException;
import by.yankavets.typingtrainer.mapper.UserMapper;
import by.yankavets.typingtrainer.model.dto.UserDto;
import by.yankavets.typingtrainer.model.entity.payload.ServerResponse;
import by.yankavets.typingtrainer.model.entity.user.User;
import by.yankavets.typingtrainer.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    private final UserMapper userMapper;

    @Autowired
    public UserController(UserService userService, UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @GetMapping
    public List<UserDto> findAll() {
        return userService.findAll();
    }

    @GetMapping("/{id}")
    public UserDto findUserById(
            @PathVariable("id") long id
    ) {
        return userMapper.toDto(userService.findById(id));
    }

    @GetMapping("/search")
    public UserDto findByEmail(
            @RequestParam("email") String email
    ) {
        return userMapper.toDto(
                userService.findByEmail(email)
        );
    }

}
