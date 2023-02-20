package by.yankavets.typingtrainer.exception.user;

public class UserNotFoundException extends RuntimeException {

    private static final String MESSAGE_WITH_ID = "User with ID %d not found";

    public UserNotFoundException(Integer id) {
        super(String.format(MESSAGE_WITH_ID, id));
    }
}
