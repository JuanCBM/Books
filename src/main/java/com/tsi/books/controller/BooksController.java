package com.tsi.books.controller;

import com.tsi.books.model.Book;
import com.tsi.books.model.Comment;
import com.tsi.books.service.BooksService;
import com.tsi.books.service.CommentsService;
import com.tsi.books.service.UserSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("books")
public class BooksController {

    @Autowired
    private UserSession userSession;

    @Autowired
    private BooksService booksService;

    @Autowired
    private CommentsService commentsService;

    @GetMapping({"/", ""})
    public String getBooks(Model model, HttpSession session) {
        model.addAttribute("books", booksService.findAll());
        model.addAttribute("isNewUser", session.isNew());

        return "index";
    }

    @GetMapping("/{id}")
    public String getBook(Model model, @PathVariable long id) {
        Book book = this.booksService.getBook(id);
        model.addAttribute("book", book);
        model.addAttribute("name",
            this.userSession.getUserName() != null ? this.userSession.getUserName()
                : "");
        model.addAttribute("showCommentTitle", book.getComments().size() > 0);

        return "book_detail";
    }

    @GetMapping("/form")
    public String bookForm(Model model) {
        model.addAttribute("booksNum", this.userSession.getNumBooks());
        return "new_book";
    }

    @PostMapping({"/", ""})
    public String createBook(Model model, Book book, HttpSession session) {
        this.booksService.save(book);
        this.userSession.addNumBooks();

        return this.getBooks(model, session);
    }

    @PostMapping("/{id}/delete")
    public String deleteBook(Model model, @PathVariable long id, HttpSession session) {
        this.booksService.deleteBook(id);

        return this.getBooks(model, session);
    }

    @PostMapping("/{bookId}/comments")
    public String newCommentBook(Model model, @PathVariable long bookId, Comment comment) {
        this.userSession.setUserName(comment.getName());
        Book book = this.booksService.getBook(bookId);
        if (book != null) {
            this.commentsService.save(book, comment);
        }

        return this.getBook(model, bookId);
    }

    @PostMapping("/{bookId}/comments/{commentId}/delete")
    public String deleteComment(Model model, @PathVariable long bookId,
        @PathVariable long commentId) {
        Book book = this.booksService.getBook(bookId);

        if (book != null) {
            this.commentsService.delete(book,commentId);
        }
        return this.getBook(model, bookId);
    }

}
