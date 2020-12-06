package com.tsi.books.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.tsi.books.model.Book;
import com.tsi.books.model.Comment;
import com.tsi.books.service.BooksService;
import com.tsi.books.service.CommentsService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Collection;

import static org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentRequest;

@RestController
@RequestMapping("/rest/books")
public class BooksRestController {

    @Autowired
    private BooksService booksService;

    @Autowired
    private CommentsService commentsService;

    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Books found",
                    content = {@Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Book.class)
                    )
                    }
            )
    })
    @GetMapping({"/", ""})
    @JsonView(Book.Basic.class)
    public ResponseEntity<Collection<Book>> getBooks() {
        return ResponseEntity.ok(booksService.findAll());
    }

    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Book found",
                    content = {@Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Book.class)
                    )
                    }
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Book not found",
                    content = {@Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Book.class)
                    )
                    }
            )
    })
    @GetMapping("/{id}")
    public ResponseEntity<Book> getBook(
            @Parameter(description = "id of the book to be searched")
            @PathVariable long id) {
        Book book = this.booksService.getBook(id);

        if (book != null) {
            return ResponseEntity.ok(this.booksService.getBook(id));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping({"/", ""})
    public ResponseEntity<Book> createBook(@RequestBody Book book) {
        this.booksService.save(book);
        URI location = fromCurrentRequest().path("/{id}").buildAndExpand(book.getId()).toUri();

        return ResponseEntity.created(location).body(book);
    }

    @PostMapping("/{bookId}/comments")
    public ResponseEntity<Book> newCommentBook(@PathVariable long bookId, @RequestBody Comment comment) {
        Book book = this.booksService.getBook(bookId);
        if (book != null) {
            this.commentsService.save(book, comment);
        }

        URI location = fromCurrentRequest().path("/{id}").buildAndExpand(comment.getId()).toUri();

        return ResponseEntity.created(location).body(book);
    }

    @GetMapping("/{bookId}/comments/{commentId}/delete")
    public ResponseEntity<Book> deleteComment(@PathVariable long bookId,
                                              @PathVariable long commentId) {
        Book book = this.booksService.getBook(bookId);

        if (book != null) {
            this.commentsService.delete(book, commentId);
            return ResponseEntity.ok(book);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/default-data")
    public ResponseEntity<String> loadDefaultData() {
        Book book = new Book("El señor de los Anillos", "Un señor con un anillo para gobernarlos a todos", "Frodo", "Edithorial", 2019);
        Book book2 = new Book("Harry Potter", "Un señor con una varita para gobernarlos a todos", "Jarry", "ThorialEdit", 2020);

        this.booksService.save(book);
        this.booksService.save(book2);

        Comment comment = new Comment("Pepe", "Me gusta mucho la peli", 5);
        Comment comment2 = new Comment("Juan", "No me gusta mucho la peli", 1);

        this.commentsService.save(book, comment);
        this.commentsService.save(book2, comment2);

        return ResponseEntity.ok("OK");
    }

}
