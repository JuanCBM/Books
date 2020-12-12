package com.tsi.books.service;

import com.tsi.books.model.Book;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class BooksService {

    private ConcurrentMap<Long, Book> books = new ConcurrentHashMap<>();
    private AtomicLong nextId = new AtomicLong();

    public Collection<Book> findAll() {
        return books.values();
    }

    public void save(Book book) {
        long id = nextId.getAndIncrement();
        book.setId(id);
        this.books.put(id, book);
    }

    public Book getBook(Long id) {
        return this.books.get(id);
    }

    public Book deleteBook(Long id) {
        return this.books.remove(id);
    }

}
