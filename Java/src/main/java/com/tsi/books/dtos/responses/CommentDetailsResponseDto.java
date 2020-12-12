package com.tsi.books.dtos.responses;

import lombok.Data;

@Data
public class CommentDetailsResponseDto {

    private Long id;
    private String comment;
    private int rating;
    private BookResponseDto book;

}
