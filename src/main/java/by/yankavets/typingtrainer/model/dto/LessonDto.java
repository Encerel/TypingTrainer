package by.yankavets.typingtrainer.model.dto;

import by.yankavets.typingtrainer.constant.ExceptionMessage;
import by.yankavets.typingtrainer.model.entity.training.Exercise;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LessonDto {


    private long lessonId;

    @NotEmpty(message = ExceptionMessage.COURSE_ID_SHOULD_NOT_BE_EMPTY)
    private long courseId;

    @NotEmpty(message = ExceptionMessage.LESSON_NAME_SHOULD_NOT_BE_EMPTY)
    @Size(min = 5, message = ExceptionMessage.LESSON_NAME_SHOULD_CONTAIN_AT_LEAST_FIVE_CHARACTER)
    private String name;

    private List<Exercise> exercises;

}
