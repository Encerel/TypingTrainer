package by.yankavets.typingtrainer.model.dto;

import by.yankavets.typingtrainer.constant.ExceptionMessage;
import by.yankavets.typingtrainer.model.entity.training.Lesson;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
public class CourseDto {

    private long id;

    @NotEmpty(message = ExceptionMessage.COURSE_NAME_SHOULD_NOT_BE_EMPTY)
    @NotNull(message = ExceptionMessage.COURSE_NAME_SHOULD_NOT_BE_NULL)
    private String name;
    @JsonIgnore
    private List<Lesson> lessons;

    @NotEmpty(message = ExceptionMessage.COURSE_DESCRIPTION_SHOULD_NOT_BE_EMPTY)
    @NotNull(message = ExceptionMessage.COURSE_DESCRIPTION_SHOULD_NOT_BE_NULL)
    private String description;
}
