package com.tsi.books.model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Data;
import nonapi.io.github.classgraph.json.Id;

@Data
public class Comment {

  @Id
  private Long id;
  @NotNull
  private String name;
  @NotNull
  private String comment;
  @NotNull
  @Size(min = 0, max = 5)
  private int rating;

  public Comment(String name, String comment, int rating) {
    this.name = name;
    this.comment = comment;
    this.rating = rating;
  }
}
