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
public class BookModeDto {

    private long id;

    private List<Excerpt> excerpts;

    private String name;

    private Excerpt excerpt;
}
