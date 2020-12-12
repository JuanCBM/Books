package com.tsi.books.service;

import com.tsi.books.model.Book;
import com.tsi.books.model.Comment;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicLong;

@Service
public class CommentsService {
    private AtomicLong nextId = new AtomicLong();

    public void save(Book book, Comment comment) {
        long id = nextId.getAndIncrement();
        comment.setId(id);
        book.addComment(comment);
    }

    public void delete(Book book, Long commentId) {
        book.deleteComment(commentId);
    }
}
