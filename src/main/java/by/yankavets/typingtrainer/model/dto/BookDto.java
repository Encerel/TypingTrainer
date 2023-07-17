package by.yankavets.typingtrainer.model.dto;

import by.yankavets.typingtrainer.constant.ExceptionMessage;
import by.yankavets.typingtrainer.model.entity.training.Excerpt;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BookDto {

    private long id;

    @NotEmpty(message = ExceptionMessage.BOOK_NAME_SHOULD_NOT_BE_EMPTY)
    @NotNull(message = ExceptionMessage.BOOK_NAME_SHOULD_NOT_BE_NULL)
    private String name;

    @NotEmpty(message = ExceptionMessage.AUTHOR_NAME_SHOULD_NOT_BE_EMPTY)
    @NotNull(message = ExceptionMessage.AUTHOR_NAME_SHOULD_NOT_BE_NULL)
    private String author;

    private List<Excerpt> excerpts;
}
