package com.tsi.books.dtos.responses;

import lombok.Data;

import java.util.Collection;

@Data
public class BookDetailsResponseDto {

    private Long id;
    private String title;
    private String resume;
    private String author;
    private String editorial;
    private int publicationYear;
    private Collection<CommentResponseDto> comments;

}
