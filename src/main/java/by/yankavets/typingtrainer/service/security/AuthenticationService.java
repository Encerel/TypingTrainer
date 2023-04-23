package by.yankavets.typingtrainer.service.security;

import by.yankavets.typingtrainer.model.dto.LoginUserDTO;
import by.yankavets.typingtrainer.model.dto.RegisterUserDTO;
import by.yankavets.typingtrainer.model.entity.payload.ServerResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;

public interface AuthenticationService {

    ResponseEntity<ServerResponse> authenticate(Authentication authentication);
    ResponseEntity<ServerResponse> register(RegisterUserDTO registerUserDTO, BindingResult bindingResult);
    ResponseEntity<ServerResponse> confirmEmailToken(String token);


}
