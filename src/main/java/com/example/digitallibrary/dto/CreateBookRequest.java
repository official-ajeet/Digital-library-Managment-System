package com.example.digitallibrary.dto;

import com.example.digitallibrary.models.Author;
import com.example.digitallibrary.models.Book;
import com.example.digitallibrary.models.enums.Genre;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateBookRequest {

    @NotBlank
    private String name;//book name
    @NotNull
    private Genre genre;
    private Integer pages;//Integer if we want to make it null

    @NotBlank
    private String authorName;
    private String authorCountry;

    @NotNull
    private String authorEmail;

    public Book to() {
        return Book.builder()
                .name(this.name)
                .pages(this.pages)
                .genre(this.genre)
                .author(//here it will accept author object - type is Author
                        Author.builder()
                              .name(this.authorName)
                              .country(this.authorCountry)
                              .email(this.authorEmail)
                              .build()
                )
                .build();
    }
}
