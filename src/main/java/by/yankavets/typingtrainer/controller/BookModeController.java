package by.yankavets.typingtrainer.controller;

import by.yankavets.typingtrainer.model.dto.BookModeDto;
import by.yankavets.typingtrainer.model.entity.payload.ServerResponse;
import by.yankavets.typingtrainer.model.entity.training.BookMode;
import by.yankavets.typingtrainer.service.training.BookModeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/book-modes")
public class BookModeController {

    @Autowired
    private BookModeService bookModeService;
    @GetMapping
    public List<BookMode> findAllModes() {
        return bookModeService.findAll();
    }

    public BookModeDto findModeById(
            @RequestParam("courseId") long id
    ) {
       return bookModeService.findById(id);
    }

    @DeleteMapping
    public ResponseEntity<ServerResponse> deleteModeById(
            @RequestParam("id") long bookModeId) {
        return bookModeService.deleteById(bookModeId);
    }

    @PostMapping
    public ResponseEntity<ServerResponse> createNewMode(
            @RequestBody BookModeDto bookModeDTO
    ) {
        return bookModeService.createMode(bookModeDTO);
    }
}
