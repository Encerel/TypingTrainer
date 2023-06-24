package by.yankavets.typingtrainer.validator;

import by.yankavets.typingtrainer.constant.ExceptionMessage;
import by.yankavets.typingtrainer.model.dto.SignUpDto;
import org.springframework.security.authentication.BadCredentialsException;

public class UserValidator {

    public static final String EMAIL_REGEX = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";

    public static boolean validate(SignUpDto user) {
        return validateEmail(user.getEmail()) &&
                validateName(user.getName()) &&
                validatePassword(user.getPassword());
    }


    private static boolean validateEmail(String email) {
        if (!email.isBlank() && email.matches(EMAIL_REGEX) && !email.isEmpty()) {
            return true;
        }
        throw new BadCredentialsException(ExceptionMessage.WRONG_EMAIL);
    }


    private static boolean validateName(String name) {

        if (name.length() > 1) {
            return true;
        }

        throw new BadCredentialsException(ExceptionMessage.WRONG_NAME);
    }

    private static boolean validatePassword(String password) {
        if (password.matches("^.{8,}$")) {
            return true;
        }

        throw new BadCredentialsException(ExceptionMessage.WRONG_PASSWORD_SIGN_UP);
    }
}


