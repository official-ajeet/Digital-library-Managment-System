package com.example.digitallibrary.models;

import com.example.digitallibrary.models.enums.TransactionStatus;
import com.example.digitallibrary.models.enums.TransactionType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
//var as _ to show property name given for back referenced

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Transaction implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String externalId;//to show the user

    @CreationTimestamp
    private Date transactionTime;

    @UpdateTimestamp
    private Date updatedOn;

    @Enumerated(value = EnumType.STRING)
    private TransactionStatus transactionStatus;

    @Enumerated(value = EnumType.STRING)
    private TransactionType transactionType;

    @ManyToOne//txn -> book - many transaction can have one book
    @JoinColumn//we want bookId to be a foreign key in this table
    @JsonIgnoreProperties({"transactionList"})
    private Book book;
    
    @ManyToOne//txt->stu - many transaction can have one student
    @JoinColumn
    @JsonIgnoreProperties({"transactionList","student"})
    private Student student;

    private double fine;

}
