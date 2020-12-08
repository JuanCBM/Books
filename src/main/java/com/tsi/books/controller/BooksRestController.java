package com.tsi.books.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.tsi.books.model.Book;
import com.tsi.books.model.Comment;
import com.tsi.books.service.BooksService;
import com.tsi.books.service.CommentsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
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


    @Operation(summary = "Get all the books")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found books",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = Book.class))
                            )
                    }),
    })
    @GetMapping({"/", ""})
    @JsonView(Book.Basic.class)
    public ResponseEntity<Collection<Book>> getBooks() {
        return ResponseEntity.ok(booksService.findAll());
    }

    @Operation(summary = "Get a book by its id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the book",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Book.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid id supplied",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Book not found",
                    content = @Content)})
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

    @Operation(summary = "Create new book")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Book created",
                    content = {@Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Book.class)
                    )
                    }
            ),

    })
    @PostMapping({"/", ""})
    public ResponseEntity<Book> createBook(@RequestBody Book book) {
        this.booksService.save(book);
        URI location = fromCurrentRequest().path("/{id}").buildAndExpand(book.getId()).toUri();

        return ResponseEntity.created(location).body(book);
    }

    @Operation(summary = "Create new comment")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Comment created",
                    content = {@Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Book.class)
                    )
                    }
            ),
    })
    @PostMapping("/{bookId}/comments")
    public ResponseEntity<Comment> newCommentBook(
            @Parameter(description = "id of the book to create comment")
            @PathVariable long bookId,
            @RequestBody Comment comment) {
        Book book = this.booksService.getBook(bookId);
        if (book != null) {
            this.commentsService.save(book, comment);
        }

        URI location = fromCurrentRequest().path("/{id}").buildAndExpand(comment.getId()).toUri();

        return ResponseEntity.created(location).body(comment);
    }

    @Operation(summary = "Delete a comment of a book by its id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Deleted the comment",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Book.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid id supplied",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Book not found",
                    content = @Content)})
    @GetMapping("/{bookId}/comments/{commentId}/delete")
    public ResponseEntity<Book> deleteComment(
            @Parameter(description = "id of the book to delete comment")
            @PathVariable long bookId,
            @Parameter(description = "id of the comment to delete")
            @PathVariable long commentId) {
        Book book = this.booksService.getBook(bookId);

        if (book != null) {
            this.commentsService.delete(book, commentId);
            return ResponseEntity.ok(book);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Delete a book by its id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Delete the book",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Book.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid id supplied",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Book not found",
                    content = @Content)})
    @GetMapping("/{bookId}/delete")
    public ResponseEntity<Book> deleteBook(
            @Parameter(description = "id of the book to delete")
            @PathVariable long bookId) {
        Book book = this.booksService.getBook(bookId);

        if (book != null) {
            this.booksService.deleteBook(bookId);
            return ResponseEntity.ok(book);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Load default data")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Initial data created",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Book.class))})})
    @PostMapping("/default-data")
    public ResponseEntity<String> loadDefaultData() {
        Book book = new Book("El señor de los Anillos",
                "Un señor con un anillo para gobernarlos a todos", "Frodo", "Edithorial", 2019);
        Book book2 = new Book("Harry Potter", "Un señor con una varita para gobernarlos a todos",
                "Jarry", "ThorialEdit", 2020);

        this.booksService.save(book);
        this.booksService.save(book2);

        Comment comment = new Comment("Pepe", "Me gusta mucho la peli", 5);
        Comment comment2 = new Comment("Juan", "No me gusta mucho la peli", 1);

        this.commentsService.save(book, comment);
        this.commentsService.save(book2, comment2);

        return ResponseEntity.ok("OK");
    }

}
