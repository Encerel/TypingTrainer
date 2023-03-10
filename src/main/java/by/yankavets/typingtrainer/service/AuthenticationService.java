package by.yankavets.typingtrainer.service;

import by.yankavets.typingtrainer.model.dto.LoginUserDTO;
import by.yankavets.typingtrainer.model.entity.payload.ServerResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;

public interface AuthenticationService {

    ResponseEntity<ServerResponse> authenticate(Authentication authentication);
}
