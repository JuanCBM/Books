package com.tsi.books.models;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
public class Book {

    public Book(String title, String resume, String author, String editorial,
                Integer publicationYear) {
        this.title = title;
        this.resume = resume;
        this.author = author;
        this.editorial = editorial;
        this.publicationYear = publicationYear;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String title;

    private String resume;

    private String author;

    private String editorial;

    private Integer publicationYear;

    @OneToMany(mappedBy = "book",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();

    public void addComment(Comment comment) {
        comments.add(comment);
        comment.setBook(this);
    }

    public void removeComment(Long commentId) {
        comments.removeIf(c -> c.getId().equals(commentId));
    }
}
