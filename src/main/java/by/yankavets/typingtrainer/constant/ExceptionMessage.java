package by.yankavets.typingtrainer.constant;

public final class ExceptionMessage {


    public static final String NO_USER_WITH_SUCH_EMAIL = "No user registered with %s email!";
    public static final String NO_USER_WITH_SUCH_ID = "No user registered with such id!";
    public static final String WRONG_PASSWORD_SIGN_IN = "Incorrect password!";

    public static final String WRONG_PASSWORD_SIGN_UP = "Password should contain at least 8 character";

    public static final String JWT_TOKEN_IS_EXPIRED = "JWT-token is expired!";

    public static final String INCORRECT_JWT_TOKEN = "JWT-token is incorrect!";

    public static final String WRONG_TOKEN = "Wrong token!";


    public static final String ACCOUNT_IS_ALREADY_CONFIRMED = "Account is already confirmed!";

    public static final String CONFIRMATION_TOKEN_EXPIRED = "Confirmation token expired!";
    public static final String PASSWORD_RESET_TOKEN_EXPIRED = "Password reset token expired!";

    public static final String BAD_CREDENTIALS = "Wrong password or email!";

    public static final String EMAIL_EXCEPTION_MESSAGE = "Failed to send email!";

    public static final String PASSWORDS_MISMATCH = "Passwords mismatch!";

    public static final String WRONG_EMAIL = "Wrong email!";
    public static final String WRONG_BOOK_ID = "No book with such id!";
    public static final String WRONG_BOOK_NAME = "No book with such name!";
    public static final String WRONG_COURSE_NAME = "No courses with such name!";
    public static final String WRONG_LESSON_ID = "No lessons with such id!";
    public static final String WRONG_COURSE_ID = "No courses with such id!";
    public static final String WRONG_EXERCISE_ID = "No exercises with such id!";

    public static String WRONG_NAME = "Name should contain 2 or more characters!";

    public static String PASSWORD_RESET_TOKEN_WAS_USED = "This jwt-token is not valid anymore!";


    private ExceptionMessage(){}
}
