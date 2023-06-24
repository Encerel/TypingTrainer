package by.yankavets.typingtrainer.service.training;

import by.yankavets.typingtrainer.model.dto.BookModeDto;
import by.yankavets.typingtrainer.model.entity.payload.ServerResponse;
import by.yankavets.typingtrainer.model.entity.training.BookMode;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface BookModeService {

    BookModeDto findById(long id);

    List<BookMode> findAll();

    ResponseEntity<ServerResponse> deleteById(long bookModeId);

    ResponseEntity<ServerResponse> createMode(BookModeDto bookModeDTO);
}
