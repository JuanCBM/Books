package com.tsi.books.model;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;

@Data
public class Comment {

  interface Full {

  }

  @JsonView(Full.class)
  private Long id;

  @JsonView(Full.class)
  private String name;

  @JsonView(Full.class)
  private String comment;

  @JsonView(Full.class)
  private int rating;

  public Comment(String name, String comment, int rating) {
    this.id = (long) 0;
    this.name = name;
    this.comment = comment;
    this.rating = rating;
  }
}
