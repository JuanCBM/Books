package com.tsi.books.model;

import lombok.Data;

@Data
public class Comment {

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
