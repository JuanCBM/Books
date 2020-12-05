package com.tsi.books.service;

import lombok.Data;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

@Component
@Data
@SessionScope
public class UserSession {

  private String userName;
  private int numBooks;

  public void addNumBooks() {
    setNumBooks(getNumBooks() + 1);
  }

}
