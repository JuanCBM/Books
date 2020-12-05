package com.tsi.books.controller;

import com.tsi.books.model.Book;
import com.tsi.books.model.Comment;
import com.tsi.books.service.BookService;
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

    @GetMapping("/")
    public String showBooks(Model model, HttpSession session) {
        model.addAttribute("books", bookService.findAll());
        model.addAttribute("isNewUser", session.isNew());

        return "index";
    }

    @GetMapping("/book/{id}")
    public String getPostDetail(Model model, @PathVariable long id) {
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

        model.addAttribute("postsNum", this.userSession.getNumPosts());
        return "new_book_form";
    }

    @PostMapping("/book/new")
    public String newPost(Model model, Book book) {

        this.bookService.add(book);
        this.userSession.addNumPosts();

        return "success";
    }

    @PostMapping("/book/{bookId}/newComment")
    public String newCommentPost(Model model, @PathVariable long bookId, Comment comment) {
        this.userSession.setUserName(comment.getName());
        Book book = this.bookService.getBook(bookId);
        if (book != null) {
            comment.setId((long) book.getCommentList().size());
            book.getCommentList().add(comment);
        }

        return this.getPostDetail(model, bookId);
    }

    @PostMapping("/book/{bookId}/comment/{commentId}/delete")
    public String deleteComment(Model model, @PathVariable long bookId,
        @PathVariable long commentId) {
        Book book = this.bookService.getBook(bookId);

        if (book != null) {
            book.getCommentList().remove((int) commentId);
        }

        return this.getPostDetail(model, bookId);
    }


}
