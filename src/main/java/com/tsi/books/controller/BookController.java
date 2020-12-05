package com.tsi.books.controller;

import com.tsi.books.model.Book;
import com.tsi.books.model.Comment;
import com.tsi.books.service.BookService;
import com.tsi.books.service.CommentService;
import com.tsi.books.service.UserSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;

@Controller
public class BookController {

    @Autowired
    private UserSession userSession;

    @Autowired
    private BookService bookService;

    @Autowired
    private CommentService commentService;

    @GetMapping("/")
    public String showBooks(Model model, HttpSession session) {
        model.addAttribute("books", bookService.findAll());
        model.addAttribute("isNewUser", session.isNew());

        return "index";
    }

    @GetMapping("/book/{id}")
    public String getBookDetail(Model model, @PathVariable long id) {
        Book book = this.bookService.getBook(id);
        model.addAttribute("book", book);
        model.addAttribute("name",
            this.userSession.getUserName() != null ? this.userSession.getUserName()
                : "");
        model.addAttribute("showCommentTitle", book.getCommentList().size() > 0);

        return "book_detail";
    }

    @GetMapping("/book/form")
    public String bookForm(Model model) {
        model.addAttribute("booksNum", this.userSession.getNumBooks());
        return "new_book";
    }

    @PostMapping("/book/new")
    public String newBook(Model model, Book book,  HttpSession session) {
        this.bookService.add(book);
        this.userSession.addNumBooks();

        return this.showBooks(model, session);
    }

    @PostMapping("/book/{id}/delete")
    public String deleteBook(Model model, @PathVariable long id, HttpSession session) {
        this.bookService.deleteBook(id);

        return this.showBooks(model, session);
    }

    @PostMapping("/book/{bookId}/newComment")
    public String newCommentBook(Model model, @PathVariable long bookId, Comment comment) {
        this.userSession.setUserName(comment.getName());
        Book book = this.bookService.getBook(bookId);
        if (book != null) {
            this.commentService.save(book, comment);
        }

        return this.getBookDetail(model, bookId);
    }

    @PostMapping("/book/{bookId}/comment/{commentId}/delete")
    public String deleteComment(Model model, @PathVariable long bookId,
        @PathVariable long commentId) {
        Book book = this.bookService.getBook(bookId);

        if (book != null) {
            this.commentService.delete(book,commentId);
        }

        return this.getBookDetail(model, bookId);
    }


}
