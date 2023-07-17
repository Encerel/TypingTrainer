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
public class ExerciseDto {


    private long exerciseId;

    @NotEmpty(message = ExceptionMessage.LESSON_ID_SHOULD_NOT_BE_EMPTY)
    private long lessonId;

    @NotNull(message = ExceptionMessage.EXERCISE_TYPING_SPEED_SHOULD_NOT_BE_NULL)
    private double typingSpeed;

    @NotNull(message = ExceptionMessage.EXERCISE_MISTAKE_COUNT_SHOULD_NOT_BE_NULL)
    private double mistakeCount;

    @NotEmpty(message = ExceptionMessage.EXERCISE_TEXT_SHOULD_NOT_BE_EMPTY)
    private String exercise;

    @NotNull(message = ExceptionMessage.EXERCISE_ACCESS_SHOULD_NOT_BE_NULL)
    private boolean access;
}
