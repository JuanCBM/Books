package com.tsi.books.service;

import com.tsi.books.model.Book;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class BookService {
    private ConcurrentMap<Long, Book> books = new ConcurrentHashMap<>();
    private AtomicLong nextId = new AtomicLong();

    public BookService() {
        save(new Book("Titulo 1", "Resumen 1", "Autor 1", "Editorial 1", 2019));
        save(new Book("Titulo 2", "Resumen 2", "Autor 2", "Editorial 2", 2020));
    }

    public void save(Book book) {
        long id = nextId.getAndIncrement();
        book.setId(id);
        this.books.put(id, book);
    }

    public Collection<Book> findAll() {
        return books.values();
    }
}
