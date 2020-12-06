package com.tsi.books.model;

import com.fasterxml.jackson.annotation.JsonView;
import java.util.ArrayList;
import java.util.List;
import javax.validation.constraints.NotNull;
import lombok.Data;
import nonapi.io.github.classgraph.json.Id;

@Data
public class Book {

    public interface Basic {

    }

    @Id
    @JsonView(Basic.class)
    private Long id;

    @NotNull
    @JsonView(Basic.class)
    private String title;
    @NotNull
    private String resume;
    @NotNull
    private String author;
    @NotNull
    private String editorial;
    @NotNull
    private Integer publicationYear;

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
