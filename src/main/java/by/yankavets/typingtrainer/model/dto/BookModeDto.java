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
public class BookModeDto {

    private long id;

    private List<Excerpt> excerpts;

    @NotNull(message = ExceptionMessage.BOOK_MODE_NAME_SHOULD_NOT_BE_NULL)
    @NotEmpty(message = ExceptionMessage.BOOK_MODE_NAME_SHOULD_NOT_BE_EMPTY)
    private String name;

    private Excerpt excerpt;

}
