package com.example.digitallibrary.models;

import com.example.digitallibrary.models.enums.Genre;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;
    @Enumerated(value = EnumType.STRING)
    private Genre genre;
    private Integer pages;

    //Book -> author - many books can be written by one author
    @ManyToOne
    @JoinColumn//this will make the primary key of author table as the foreign key of book table - by default it will join by the primary key - if want to join with some other column in table then can use (name = "column name")
    @JsonIgnoreProperties({"bookList"}) // to ignore bi relation - strict infinite looping, it is a forward reference not a back reference (back is when mappedBy is used)
    private Author author; //m:1 from book to author

    @ManyToOne
    @JoinColumn//to specify the name of the foreign key column by default fk will student's id
    @JsonIgnoreProperties({"bookList"})//ignore the booklist from here
    private Student my_student;//m:1 from book to student

    @CreationTimestamp
    private Date createdOn;
    @UpdateTimestamp
    private Date updatedOn;

    //creating a back reference  (bi-direction relationships)
    @OneToMany(mappedBy = "book", fetch = FetchType.LAZY)//fetch type set to eager - this will search from everywhere (because we were getting 500 but deletion was succesfull)
    @JsonIgnoreProperties({"student","book"})
    List<Transaction> transactionList;


}
