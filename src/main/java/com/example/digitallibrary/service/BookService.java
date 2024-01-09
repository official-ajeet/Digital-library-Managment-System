package com.example.digitallibrary.service;

import com.example.digitallibrary.dto.CreateBookRequest;
import com.example.digitallibrary.dto.SearchBookRequest;
import com.example.digitallibrary.models.Author;
import com.example.digitallibrary.models.Book;
import com.example.digitallibrary.models.Student;
import com.example.digitallibrary.models.enums.Genre;
import com.example.digitallibrary.repository.AuthorRepository;
import com.example.digitallibrary.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Arrays;
import java.util.List;

@Service //annotating the service class
public class BookService {

    @Autowired
    BookRepository bookRepository;

    @Autowired
    AuthorService authorService;//to write business logic, - not saving author blindly
    @Autowired
    AuthorRepository authorRepository;

//USED BY CONTROLLER TO CREATE A BOOK
    //For Book create function -> 2(if author exists)  or 3(if author not exists) db calls will be there
    public Book create(CreateBookRequest createBookRequest){// pass it to bookRepository - autowired above
        //first we need to store the author - then he can write the books - otherwise we will get error from db side - unsaved transient from hibernate or foreign key constraints violation from db
        Book book = createBookRequest.to();//dto to model conversion

        Author author = authorService.createOrGet(book.getAuthor());//first we are inserting an author
        book.setAuthor(author);//setting an author for a book
        return bookRepository.save(book);//then we are saving a book
    }
//USED FROM TRANSACTION SERVICE FOR UPDATING A BOOK  - UPSERT
    public void assignBookToStudent(Book book, Student student){
        bookRepository.assignBookToStudent(book.getId(), student);
    }

    public void unassignBookFromStudent(Book book){
        bookRepository.unassignBook(book.getId());
    }

    public Book delete(int bookId) {
        Book book = this.bookRepository.findById(bookId).orElse(null);
        bookRepository.deleteById(bookId);//return type is void
        return book;//returning a book that is deleted
    }

    @PersistenceContext
    EntityManager entityManager;
    public List<Book>getAllBooks(){
        return entityManager.createQuery("SELECT b FROM Book b", Book.class).getResultList();
    }

    public List<Book> search(SearchBookRequest searchBookRequest) throws Exception {

        switch (searchBookRequest.getSearchKey()){
            case "name":
                if(searchBookRequest.isAvailable()){//checking the book is available or not
                    return bookRepository.findByNameAndStudentIsNull(searchBookRequest.getSearchValue());
                }
                return bookRepository.findByName(searchBookRequest.getSearchValue());

            case "genre":
                return bookRepository.findByGenre(Genre.valueOf(searchBookRequest.getSearchValue()));

            case "id":
                Book book = bookRepository.findById(Integer.parseInt(searchBookRequest.getSearchValue())).orElse(null);
                return Arrays.asList(book);

            case "pages":
                return bookRepository.findByPages(Integer.parseInt(searchBookRequest.getSearchValue()));

            case "author_name":
                return bookRepository.findByAuthorName(searchBookRequest.getSearchValue());


            default:
                throw new Exception("Invalid Search Key");

        }
    }
}
