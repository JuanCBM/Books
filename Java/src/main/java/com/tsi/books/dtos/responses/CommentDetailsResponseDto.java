package com.tsi.books.dtos.responses;

import lombok.Data;

@Data
public class CommentDetailsResponseDto {

    private Long id;
    private String content;
    private int rating;
    private BookResponseDto book;

}
