package com.tsi.books.model;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
public class Book {

    public interface Basic {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonView(Basic.class)
    private Long id;

    @JsonView(Basic.class)
    private String title;

    private String resume;

    private String author;

    private String editorial;

    private Integer publicationYear;
    
    @Transient
    private List<Comment> comments = new ArrayList<>();

    public Book(String title, String resume, String author, String editorial,
                Integer publicationYear) {
        this.title = title;
        this.resume = resume;
        this.author = author;
        this.editorial = editorial;
        this.publicationYear = publicationYear;
    }

    public void addComment(Comment comment) {
        this.comments.add(comment);
    }

    public void deleteComment(Long commentId) {
        this.comments.removeIf(c -> c.getId().equals(commentId));
    }
}
