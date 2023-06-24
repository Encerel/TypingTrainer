package by.yankavets.typingtrainer.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ExcerptDto {

    private long excerptId;
    private long bookId;
    private String excerpt;

}
