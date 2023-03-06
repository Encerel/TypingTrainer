package by.yankavets.typingtrainer.exception.user;

public class UserIsExistException extends RuntimeException {

    private static final String MESSAGE_WITH_USERNAME = "User with username %s is already exist";

    public UserIsExistException(String email) {
        super(String.format(MESSAGE_WITH_USERNAME, email));
    }
}
