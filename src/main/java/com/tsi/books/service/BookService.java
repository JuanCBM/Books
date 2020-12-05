package com.tsi.books.service;

import com.tsi.books.model.Book;
import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class BookService {
    private ConcurrentMap<Long, Book> posts = new ConcurrentHashMap<>();
    private AtomicLong nextId = new AtomicLong();

    public BookService() {
        Book book = new Book();
    }

}
