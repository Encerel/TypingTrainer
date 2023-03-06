package by.yankavets.typingtrainer.model.entity.payload.response;

import by.yankavets.typingtrainer.model.entity.payload.ServerResponse;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;


@Getter
public class SuccessfulMessage implements ServerResponse {

    public static final SuccessfulMessage MESSAGE_USER_CREATED_SUCCESSFUL =
            formSuccessCreateResponse("User", HttpStatus.CREATED);

    private final String message;
    private final int status;


    public SuccessfulMessage( String message, int status) {
        this.message = message;
        this.status = status;
    }


    public static SuccessfulMessage formSuccessCreateResponse(String name, HttpStatus status) {
        return new SuccessfulMessage(String.format("%s created successfully!", name), status.value());
    }
}
