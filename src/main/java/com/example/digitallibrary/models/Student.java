package com.example.digitallibrary.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class Student implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String contact;

    @CreationTimestamp
    private Date createdOn;

    @UpdateTimestamp
    private Date updatedOn;

    //making by-direction relationship so , no need to be there to create new column
    @OneToMany(mappedBy = "my_student",fetch = FetchType.EAGER)//eager to search from everywhere
    @JsonIgnoreProperties({"transactionList","bookList","my_student"})
    private List<Book> bookList;//list of student have issued

    @OneToMany(mappedBy = "student")
    @JsonIgnoreProperties({"book","student"})
    List<Transaction> transactionList;//list of txn made by a student

    private Date validity;//for card validity

    @OneToOne
    @JoinColumn
    private  SecuredUser securedUser;
}
