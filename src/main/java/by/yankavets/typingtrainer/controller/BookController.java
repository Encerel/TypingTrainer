package by.yankavets.typingtrainer.controller;

import by.yankavets.typingtrainer.model.dto.BookDto;
import by.yankavets.typingtrainer.model.entity.training.Book;
import by.yankavets.typingtrainer.model.entity.payload.ServerResponse;
import by.yankavets.typingtrainer.service.training.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {

    @Autowired
    private BookService bookService;

    @GetMapping
    public List<Book> findAllBooks() {
        return bookService.findAll();
    }

    @GetMapping("/search")
    public Book findBookByName(
            @RequestParam("name") String bookName) {
        return bookService.findByName(bookName);
    }

    @DeleteMapping
    public ResponseEntity<ServerResponse> deleteBookById(
            @RequestParam("id") long bookId) {
        return bookService.deleteById(bookId);
    }

    @PostMapping("/new")
    public ResponseEntity<ServerResponse> createNewBook(
            @RequestBody BookDto bookDTO
    ) {
        return bookService.save(bookDTO);
    }

    @PutMapping
    public  ResponseEntity<ServerResponse> editBook(
            @RequestBody BookDto bookDTO
    ) {
        return bookService.editBook(bookDTO);
    }



}
