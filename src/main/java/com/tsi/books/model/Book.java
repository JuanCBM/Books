package com.tsi.books.model;

import java.util.ArrayList;
import java.util.List;
import lombok.Data;

@Data
public class Book {

    private Long id;
    private String title;
    private String resume;
    private String author;
    private String editorial;
    private Integer publicationYear;

    private List<Comment> commentList = new ArrayList<>();


    public Book(String title, String resume, String author, String editorial,
        Integer publicationYear) {
        this.title = title;
        this.resume = resume;
        this.author = author;
        this.editorial = editorial;
        this.publicationYear = publicationYear;
    }
}
