package com.tsi.books.model;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;
    private String comment;
    private int rating;

    public Comment(String name, String comment, int rating) {
        this.name = name;
        this.comment = comment;
        this.rating = rating;
    }
}
