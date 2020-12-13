package com.tsi.books.dtos.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookRequestDto {

    private String title;
    private String resume;
    private String author;
    private String editorial;
    private int publicationYear;

}
