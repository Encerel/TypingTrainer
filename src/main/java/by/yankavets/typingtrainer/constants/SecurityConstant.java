package by.yankavets.typingtrainer.constants;

import org.springframework.beans.factory.annotation.Value;

public final class SecurityConstant {

    @Value("${jwt_secret}")
    public static String SECRET_KEY;

    public static final String AUTH_HEADER = "Authorization";

    public static final String JWT_ISSUER = "Typing Trainer";

    public static final String BEARER_HEADER = "Bearer ";


    private SecurityConstant(){}
}
