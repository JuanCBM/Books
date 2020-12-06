package com.tsi.books.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;

@Data
@ApiModel("Model Book")
public class Book {

    @ApiModelProperty(value = "the book's id", required = true)
    private Long id;
    @ApiModelProperty(value = "the book's title", required = true)
    private String title;
    @ApiModelProperty(value = "the book's resume", required = true)
    private String resume;
    @ApiModelProperty(value = "the book's author", required = true)
    private String author;
    @ApiModelProperty(value = "the book's editorial", required = true)
    private String editorial;
    @ApiModelProperty(value = "the book's publicationYear", required = true)
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

    public void addComment(Comment comment){
        this.commentList.add(comment);
    }

    public void deleteComment(Long commentId) {
        this.commentList.removeIf(c -> c.getId().equals(commentId));
    }
}
