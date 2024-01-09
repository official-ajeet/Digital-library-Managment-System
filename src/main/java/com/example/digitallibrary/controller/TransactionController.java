package com.example.digitallibrary.controller;

import com.example.digitallibrary.models.SecuredUser;
import com.example.digitallibrary.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TransactionController {
    @Autowired
    TransactionService transactionService;

    //create transaction - here we will issue a book to a student
    @PostMapping("/transaction/issue")//accessible by student only
    public String issueTxn(@RequestParam ("book-name") String bookName) throws Exception {
        //to get the student id
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        SecuredUser securedUser = (SecuredUser) authentication.getPrincipal();

        int studentId = securedUser.getStudent().getId();
        return transactionService.issueTxn(bookName,studentId);
    }

    @PostMapping("/transaction/return")
    public String returnTxn(@RequestParam("bookId") int bookId) throws Exception {
        //to get the student id
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        SecuredUser securedUser = (SecuredUser) authentication.getPrincipal();

        int studentId = securedUser.getStudent().getId();
        return  transactionService.returnTxn(bookId,studentId);
    }
}
