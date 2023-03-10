package by.yankavets.typingtrainer.exception.auth;

public class UserIsAlreadyExistException extends RuntimeException {

    private static final String MESSAGE_WITH_USERNAME = "User with username %s is already exist";

    public UserIsAlreadyExistException(String email) {
        super(String.format(MESSAGE_WITH_USERNAME, email));
    }
}
