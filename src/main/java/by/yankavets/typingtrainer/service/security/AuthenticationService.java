package by.yankavets.typingtrainer.service.security;

import by.yankavets.typingtrainer.model.dto.SignUpDto;
import by.yankavets.typingtrainer.model.dto.ResetPasswordDto;
import by.yankavets.typingtrainer.model.entity.payload.ServerResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

public interface AuthenticationService {

    ResponseEntity<ServerResponse> authenticate(Authentication authentication);
    ResponseEntity<ServerResponse> register(SignUpDto signUpDTO);
    ResponseEntity<ServerResponse> sendConfirmEmailToken(String token);
    ResponseEntity<ServerResponse> sendPasswordResetCode(String email);

    ResponseEntity<ServerResponse> resetPassword(ResetPasswordDto resetPasswordDTO);
}
