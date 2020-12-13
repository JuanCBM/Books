package com.tsi.books.dtos.requests;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CommentRequestDto {

    private String nick;
    private String content;
    private int rating;

}
