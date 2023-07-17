package by.yankavets.typingtrainer.model.dto;

import by.yankavets.typingtrainer.constant.ExceptionMessage;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ExcerptDto {

    private long excerptId;

    @NotEmpty(message = ExceptionMessage.BOOK_ID_SHOULD_NOT_BE_EMPTY)
    @NotNull(message = ExceptionMessage.BOOK_ID_SHOULD_NOT_BE_NULL)
    private long bookId;

    @NotEmpty(message = ExceptionMessage.EXCERPT_TEXT_SHOULD_NOT_BE_EMPTY)
    private String excerpt;

}
