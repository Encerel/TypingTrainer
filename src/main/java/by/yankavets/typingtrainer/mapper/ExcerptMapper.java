package by.yankavets.typingtrainer.mapper;

import by.yankavets.typingtrainer.model.dto.ExcerptDto;
import by.yankavets.typingtrainer.model.entity.training.Excerpt;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = "spring",
        uses = {BookMapper.class}
)
public interface ExcerptMapper {

    Excerpt toEntity(ExcerptDto excerptDto);

    ExcerptDto toDto(Excerpt excerpt);

    List<ExcerptDto> toDtoList(List<Excerpt> excerpts);

}
