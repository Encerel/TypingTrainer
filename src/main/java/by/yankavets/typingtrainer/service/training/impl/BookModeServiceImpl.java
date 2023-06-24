package by.yankavets.typingtrainer.service.training.impl;

import by.yankavets.typingtrainer.constant.ExceptionMessage;
import by.yankavets.typingtrainer.constant.Message;
import by.yankavets.typingtrainer.exception.TrainingDataException;
import by.yankavets.typingtrainer.mapper.BookModeMapper;
import by.yankavets.typingtrainer.model.dto.BookModeDto;
import by.yankavets.typingtrainer.model.entity.payload.ServerResponse;
import by.yankavets.typingtrainer.model.entity.payload.response.MessageServerResponse;
import by.yankavets.typingtrainer.model.entity.training.BookMode;
import by.yankavets.typingtrainer.repository.BookModeRepository;
import by.yankavets.typingtrainer.service.training.BookModeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class BookModeServiceImpl implements BookModeService {

    @Autowired
    private BookModeRepository bookModeRepository;

    @Autowired
    private BookModeMapper bookModeMapper;

    @Override
    public List<BookMode> findAll() {
        return bookModeRepository.findAll();
    }

    @Override
    public BookModeDto findById(long id) {
        BookMode bookModeFromDB = bookModeRepository.findById(id).orElseThrow(
                (() -> new TrainingDataException(ExceptionMessage.WRONG_BOOK_ID))
        );
        return bookModeMapper.toDto(bookModeFromDB);
    }

    @Transactional
    @Override
    public ResponseEntity<ServerResponse> deleteById(long bookModeId) {

        bookModeRepository.deleteById(bookModeId);

        ServerResponse response = MessageServerResponse.builder()
                .message(Message.BOOK_MODE_HAS_DELETED)
                .status(HttpStatus.OK.value())
                .build();
        return ResponseEntity.ok(response);
    }

    @Transactional
    @Override
    public ResponseEntity<ServerResponse> createMode(BookModeDto bookModeDTO) {

        BookMode bookMode = new BookMode();
        bookMode.setName(bookModeDTO.getName());
        bookModeRepository.save(bookMode);

        ServerResponse response = MessageServerResponse.builder()
                .message(Message.BOOK_MODE_HAS_CREATED)
                .status(HttpStatus.OK.value())
                .build();
        return ResponseEntity.ok(response);
    }
}
