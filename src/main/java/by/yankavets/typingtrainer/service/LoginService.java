package by.yankavets.typingtrainer.service;

import by.yankavets.typingtrainer.model.dto.LoginUserDTO;
import by.yankavets.typingtrainer.model.entity.User;

import java.util.Optional;

public interface LoginService {

    void login(LoginUserDTO userDTO, String userFromDBPassword);

}
