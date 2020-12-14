package com.tsi.books.services;

import com.tsi.books.dtos.requests.BookRequestDto;
import com.tsi.books.dtos.requests.CommentRequestDto;
import com.tsi.books.dtos.responses.BookDetailsResponseDto;
import com.tsi.books.dtos.responses.BookResponseDto;

import java.util.Collection;

public interface BookService {
    Collection<BookResponseDto> findAll();

    BookDetailsResponseDto save(BookRequestDto bookRequestDto);

    BookDetailsResponseDto findById(Long id);

    BookDetailsResponseDto deleteById(Long id);

    BookDetailsResponseDto addComment(Long idBook, CommentRequestDto comment);

    BookDetailsResponseDto deleteComment(Long idBook, Long commentId);

    BookDetailsResponseDto update(Long idBook, BookRequestDto bookRequestDto);
}
