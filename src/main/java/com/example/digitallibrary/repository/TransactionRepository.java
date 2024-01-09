package com.example.digitallibrary.repository;

import com.example.digitallibrary.models.Book;
import com.example.digitallibrary.models.Student;
import com.example.digitallibrary.models.Transaction;
import com.example.digitallibrary.models.enums.TransactionStatus;
import com.example.digitallibrary.models.enums.TransactionType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction,Integer> {
    Transaction findTopByStudentAndBookAndTransactionTypeAndTransactionStatusOrderByTransactionTimeDesc(
            Student student, Book book, TransactionType transactionType, TransactionStatus transactionStatus
    );
}
