package com.example.digitallibrary.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    //taking email and making it unique and not nullable
    // so that we can put some logic to not add authors again and again who writes more than one books
    @Column(unique = true, nullable = false)
    private String email;

    private String name;
    private String country;

    @CreationTimestamp
    private Date addedOn;

    //author -> book - one author can write many books and many books can be written by one author
    @OneToMany(mappedBy = "author")//bi-direction relationship - you don't need to create a new column for bookList in the author table , just create a back reference
    @JsonIgnoreProperties({"author"})//ignoring a back reference , we don't want data of author again , obviously getting book then author was inserted
    private List<Book> bookList;// to get how many books the author has written


}
