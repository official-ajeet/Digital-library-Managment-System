//package com.example.digitallibrary;
//
//
//import com.example.digitallibrary.repository.BookRepository;
//import com.example.digitallibrary.repository.TransactionRepository;
//import com.example.digitallibrary.service.BookService;
//import com.example.digitallibrary.service.StudentService;
//import com.example.digitallibrary.service.TransactionService;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.MockitoJUnitRunner;
//
//@RunWith(MockitoJUnitRunner.class)//tells how you want to execute
//public class TransactionServiceTest {
//
//    @InjectMocks //acts as autowired, create an actual instance
//    TransactionService transactionService;
//
//    //only create mocks for direct dependent classes
//    @Mock//it creates a dummy instance
//    StudentService studentService;
//
//    @Mock
//    BookService bookService;
//
//    @Mock
//    BookRepository bookRepository;
//
////    @Test
//    public void issueTxn_test() throws Exception {
////        transactionService.issueTxn("abc",1);
//    }
//}
