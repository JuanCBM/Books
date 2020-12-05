package com.tsi.books.controller;

import com.tsi.books.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BookController {

    @Autowired
    private BookService bookService;

    @GetMapping("/")
    public String showBooks(Model model) {
        model.addAttribute("books", bookService.findAll());

        return "index";
    }
}
