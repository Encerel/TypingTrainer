package by.yankavets.typingtrainer.constant;

public final class ExceptionMessage {


    public static final String WRONG_EMAIL = "No user registered with such email!";
    public static final String WRONG_PASSWORD = "Incorrect password!";

    public static final String JWT_TOKEN_IS_EXPIRED = "JWT-token is expired!";

    public static final String INCORRECT_JWT_TOKEN = "JWT-token is incorrect!";

    public static final String WRONG_TOKEN = "Wrong token!";

    public static final String ACCOUNT_IS_ALREADY_CONFIRMED = "Account is already confirmed!";

    public static final String CONFIRMATION_TOKEN_EXPIRED = "Confirmation token expired!";
    public static final String PASSWORD_RESET_TOKEN_EXPIRED = "Password reset token expired!";

    public static final String BAD_CREDENTIALS = "Wrong password or email!";

    public static final String EMAIL_EXCEPTION_MESSAGE = "Failed to send email!";

    public static final String PASSWORDS_MISMATCH = "Passwords mismatch!";


    private ExceptionMessage(){}
}
