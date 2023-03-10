package by.yankavets.typingtrainer.service;

import by.yankavets.typingtrainer.model.entity.User;


public interface UserService {
    User findByEmail(String email);
}
