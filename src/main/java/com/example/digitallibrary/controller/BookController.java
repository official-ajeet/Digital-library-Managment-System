package com.example.digitallibrary.controller;

import com.example.digitallibrary.dto.CreateBookRequest;
import com.example.digitallibrary.dto.SearchBookRequest;
import com.example.digitallibrary.models.Book;
import com.example.digitallibrary.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/book")//common endpoint
public class BookController {//we will write only create , read, delete
    @Autowired
    BookService bookService;

    @PostMapping("")
    public Book createBook(@RequestBody @Valid CreateBookRequest createBookRequest){//pass this to service layer , autowired above
        return bookService.create(createBookRequest);
    }

    @DeleteMapping("/{bookId}")
    public Book deleteBook(@PathVariable ("bookId") int bookId){
        return bookService.delete(bookId);
    }

    @GetMapping("/all")
    public List<Book> getAllBooks(){
        return bookService.getAllBooks();
    }


    //tricky one , imp - search - by filters
    @GetMapping("/search")
    public List<Book> searchBooks(@RequestBody @Valid SearchBookRequest searchBookRequest) throws Exception {
        return bookService.search(searchBookRequest);
    }
}
