package com.tsi.books.service;

import com.tsi.books.model.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class BookService {

    private ConcurrentMap<Long, Book> books = new ConcurrentHashMap<>();
    private AtomicLong nextId = new AtomicLong();

    @Autowired
    private CommentService commentService;

    public BookService() {
        save(new Book("El señor de los Anillos", "Un señor con un anillo para gobernarlos a todos", "Frodo", "Edithorial", 2019));
        save(new Book("Harry Potter", "Un señor con una varita para gobernarlos a todos", "Jarry", "ThorialEdit", 2020));
    }

    private void save(Book book) {
        long id = nextId.getAndIncrement();
        book.setId(id);
        this.books.put(id, book);
    }

    public Collection<Book> findAll() {
        return books.values();
    }

    public void add(Book book) {
        this.save(book);
    }

    public Book getBook(Long id) {
        return this.books.get(id);
    }

    public Book deleteBook(Long id) {
        return this.books.remove(id);
    }
}
