package by.yankavets.typingtrainer.service.email;

public interface EmailService {

    void send(String to, String message);

    String composeLetter(String username, String token);

}
