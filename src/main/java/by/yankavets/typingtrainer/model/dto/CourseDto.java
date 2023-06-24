package by.yankavets.typingtrainer.model.dto;

import by.yankavets.typingtrainer.model.entity.training.Lesson;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
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
    private String name;
    @JsonIgnore
    private List<Lesson> lessons;
    private String description;
}
