package com.tsi.books.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Book {

    private Long id;
    private String title;
    private String resume;
    private String author;
    private String editorial;
    private Integer publicationYear;
}
