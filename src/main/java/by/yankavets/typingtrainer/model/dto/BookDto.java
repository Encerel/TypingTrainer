package by.yankavets.typingtrainer.model.dto;

import by.yankavets.typingtrainer.model.entity.training.Excerpt;
import com.fasterxml.jackson.annotation.JsonInclude;
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
    private String name;
    private String author;
    private List<Excerpt> excerpts;
}
