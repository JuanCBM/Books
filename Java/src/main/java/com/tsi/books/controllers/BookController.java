package com.tsi.books.controllers;

import com.tsi.books.dtos.requests.BookRequestDto;
import com.tsi.books.dtos.requests.CommentRequestDto;
import com.tsi.books.dtos.responses.BookDetailsResponseDto;
import com.tsi.books.dtos.responses.BookResponseDto;
import com.tsi.books.services.BookService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Collection;

import static org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentRequest;

@RestController
@RequestMapping("books")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping({"/", ""})
    public ResponseEntity<Collection<BookResponseDto>> list() {
        return ResponseEntity.ok(this.bookService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookDetailsResponseDto> get(@PathVariable long id) {
        return ResponseEntity.ok(this.bookService.findById(id));
    }

    @PostMapping({"/", ""})
    public ResponseEntity<BookDetailsResponseDto> create(@RequestBody BookRequestDto bookRequestDto) {
        BookDetailsResponseDto book = this.bookService.save(bookRequestDto);
        URI location = fromCurrentRequest().path("/{id}").buildAndExpand(book.getId()).toUri();

        return ResponseEntity.created(location).body(book);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BookDetailsResponseDto> delete(@PathVariable long id) {
        return ResponseEntity.ok(this.bookService.deleteById(id));
    }

    @PostMapping("/{idBook}/comments")
    public ResponseEntity<BookDetailsResponseDto> createComment(@PathVariable long idBook, @RequestBody CommentRequestDto comment) {
        URI location = fromCurrentRequest().path("/{id}").buildAndExpand(idBook).toUri();

        return ResponseEntity.created(location).body(this.bookService.addComment(idBook, comment));
    }

    @DeleteMapping("/{bookId}/comments/{commentId}")
    public ResponseEntity<BookDetailsResponseDto> deleteComment(
            @PathVariable long bookId,
            @PathVariable long commentId) {
        return ResponseEntity.ok(this.bookService.deleteComment(bookId, commentId));

    }

}
