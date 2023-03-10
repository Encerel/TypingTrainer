package by.yankavets.typingtrainer.service;

import by.yankavets.typingtrainer.model.dto.RegisterUserDTO;
import by.yankavets.typingtrainer.model.entity.User;
import org.springframework.validation.BindingResult;

public interface RegistrationService {

    User register(RegisterUserDTO registerUserDTO, BindingResult bindingResult);
}
