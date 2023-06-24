package by.yankavets.typingtrainer.util;

import by.yankavets.typingtrainer.model.entity.training.CompletionStatus;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.stream.Stream;

//@Converter(autoApply = true)
public class CompletionStatusConverter {
//        implements AttributeConverter<CompletionStatus, String> {
//    @Override
//    public String convertToDatabaseColumn(CompletionStatus completionStatus) {
//        return completionStatus == null ? null : completionStatus.getStatus();
//    }
//
//    @Override
//    public CompletionStatus convertToEntityAttribute(String completionStatus) {
//
//        if (completionStatus == null) {
//            return null;
//        }
//        return Stream.of(CompletionStatus.values())
//                .filter(status -> status.getStatus().equals(completionStatus))
//                .findFirst()
//                .orElseThrow(IllegalArgumentException::new);
//    }
}
