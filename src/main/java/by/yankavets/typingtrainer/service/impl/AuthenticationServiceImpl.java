package by.yankavets.typingtrainer.service.impl;

import by.yankavets.typingtrainer.exception.ExceptionMessage;
import by.yankavets.typingtrainer.model.dto.UserDTO;
import by.yankavets.typingtrainer.model.entity.User;
import by.yankavets.typingtrainer.model.entity.payload.ServerResponse;
import by.yankavets.typingtrainer.model.entity.payload.response.AuthenticationResponse;
import by.yankavets.typingtrainer.repository.UserRepository;
import by.yankavets.typingtrainer.service.AuthenticationService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {


    private final UserRepository userRepository;


    private final ModelMapper modelMapper;

    @Autowired
    public AuthenticationServiceImpl(UserRepository userRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
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

        Optional<User> userFromDB = userRepository.findByEmail(authentication.getName());

        if (userFromDB.isEmpty()) {
            throw new UsernameNotFoundException(ExceptionMessage.WRONG_EMAIL);
        }

        User foundUser = userFromDB.get();

        serverResponse = AuthenticationResponse
                .builder()
                .userDTO(modelMapper.map(foundUser, UserDTO.class))
                .status(HttpStatus.OK.value())
                .build();


        return ResponseEntity.ok(serverResponse);
    }
}
