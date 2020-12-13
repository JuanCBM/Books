package com.tsi.books.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Entity
@AllArgsConstructor
@EqualsAndHashCode
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String content;
    private int rating;

    @ManyToOne
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    private Book book;

}
