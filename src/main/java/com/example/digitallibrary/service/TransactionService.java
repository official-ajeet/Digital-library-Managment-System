package com.example.digitallibrary.service;

import com.example.digitallibrary.dto.SearchBookRequest;
import com.example.digitallibrary.models.Book;
import com.example.digitallibrary.models.Student;
import com.example.digitallibrary.models.Transaction;
import com.example.digitallibrary.models.enums.TransactionStatus;
import com.example.digitallibrary.models.enums.TransactionType;
import com.example.digitallibrary.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class TransactionService {
    @Autowired BookService bookService;
    @Autowired StudentService studentService;
    @Autowired
    TransactionRepository transactionRepository;

    @Value("${student.issue.max_books}")
    private int maxBooksForIssuance;

    @Value("${student.issue.number_of_days}")
    private int numberOfDaysForIssuance;


    /*APPROACH FOR THE TRANSACTION
    1. get the book and student details first - get the list of books and check if the requested book is present or not
    2. validation
    3. create a transaction with status pending
    4. assign a book to the student
    5. update the transaction
     */

    public String issueTxn(String bookName, int studentId) throws Exception {
        //to get the book details
        List<Book> bookList;
        try {
            bookList = bookService.search(
                    SearchBookRequest.builder()
                            .searchKey("name")
                            .searchValue(bookName)
                            .operator("=")
                            .available(true)
                            .build()
            );
        } catch (Exception e) {
            throw new Exception("Book Not Found");
        }

        //now get the student details for the transaction
        Student student = studentService.get(studentId);
        //now put the validations here

        if(student.getBookList() != null && student.getBookList().size() >= maxBooksForIssuance){
            throw new Exception("Book Limit Reached");
        }

        if(bookList.isEmpty()){
            throw new Exception("Not able to find any book in the library");
        }

        Book book = bookList.get(0);

        Transaction transaction = Transaction.builder()
                .externalId(UUID.randomUUID().toString())
                .transactionType(TransactionType.ISSUE)
                .student(student)
                .book(book)
                .transactionStatus(TransactionStatus.PENDING)
                .build();

        //we have put the code in try if txn will success and catching if it will give some error then in catch block we are setting failed status
            transaction = transactionRepository.save(transaction);
        try {

            //now assign the book to the student
            book.setMy_student(student);
            bookService.assignBookToStudent(book,student);

            //update the transaction status
            transaction.setTransactionStatus(TransactionStatus.SUCCESS);
        }catch (Exception e){
            e.printStackTrace();
            transaction.setTransactionStatus(TransactionStatus.FAILED);
        }finally {
            return transactionRepository.save(transaction).getExternalId();
        }
    }

    public String returnTxn(int bookId, int studentId) throws Exception {
        //mainly we need to calculate fine here while returning a book
        Book book;
        try {
            book = this.bookService.search(
                    SearchBookRequest.builder()
                            .searchKey("id")
                            .searchValue(String.valueOf(bookId))
                            .build()
            ).get(0);//it is returning a list (generic api is there )that why we need to get the 0th index

        }catch (Exception e){
            throw new Exception("not able to fetch book details");
        }

        //now we need to do some validations
        if(book.getMy_student() == null || book.getMy_student().getId() != studentId){//we want to verify this particular student which is coming in the request - let say book is issued to student1 we some other student is returning the book, then we will not allow that student
            throw new Exception("Book is not assigned to this student ");
        }

        Student student = this.studentService.get(studentId);

        Transaction transaction = Transaction.builder()
                .externalId(UUID.randomUUID().toString())
                .transactionType(TransactionType.RETURN)
                .student(student)
                .transactionStatus(TransactionStatus.PENDING)
                .build();

        transaction = transactionRepository.save(transaction);


        //now after validations this is confirmed that this book is assigned to this student

        //get the corresponding transaction
        //This code is retrieving the most recent successful issue transaction for a particular student and book from a repository named transactionRepository. The retrieved transaction is stored in the variable issueTransaction./
        Transaction issueTransaction = this.transactionRepository.findTopByStudentAndBookAndTransactionTypeAndTransactionStatusOrderByTransactionTimeDesc(student, book, TransactionType.ISSUE, TransactionStatus.SUCCESS);

        //now do the fine calculation

        long issueTxnInMillis = issueTransaction.getTransactionTime().getTime();

        //diff
        long currentTimeMillis = System.currentTimeMillis();
        long timeDifferenceInMillis = currentTimeMillis - issueTxnInMillis;

        //convert time into days
        long timeDifferenceInDays = TimeUnit.DAYS.convert(timeDifferenceInMillis, TimeUnit.MILLISECONDS);

        double fine = 0.0;
        if(timeDifferenceInDays >= numberOfDaysForIssuance){
            fine = (timeDifferenceInDays - numberOfDaysForIssuance) *1.0;
        }

        try {
            book.setMy_student(null);
            this.bookService.unassignBookFromStudent(book);
            transaction.setTransactionStatus(TransactionStatus.SUCCESS);
            return transaction.getExternalId();
        }catch (Exception e){
            e.printStackTrace();
            transaction.setTransactionStatus(TransactionStatus.FAILED);
        }finally {
            transaction.setFine(fine);
            return transactionRepository.save(transaction).getExternalId();
        }

        //NOTE:- if a student do more transactions for same book with status issue or return then we will focus only on the latest transaction - that's why we will include status and type also in our query
    }



}
