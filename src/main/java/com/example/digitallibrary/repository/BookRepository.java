package com.example.digitallibrary.repository;

import com.example.digitallibrary.models.Book;
import com.example.digitallibrary.models.Student;
import com.example.digitallibrary.models.enums.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

public interface BookRepository extends JpaRepository<Book,Integer> {
    List<Book>findByName(String name);

    List<Book> findByAuthorName(String authorName);

    //using query
    @Query("select b from Book b where b.name =:name and b.my_student is null")
    List<Book> findByNameAndStudentIsNull(String name);

    List<Book> findByGenre(Genre genre);

    List<Book> findByPages(int pages);

    //again we are checking the book is available or not

    //for any dml query we need to apply these annotations for sure
    @Modifying // required for DML support
    @Transactional // for updating any data - if not used the exception will come - InvalidDataAccessApiUsageException
    @Query("update Book b set b.my_student = ?2 where b.id = ?1 and b.my_student is null")
    void assignBookToStudent(int bookId, Student student);


    @Modifying // required for DML support
    @Transactional // for updating any data - if not used the exception will coome - InvalidDataAccessApiUsageException
    @Query("update Book b set b.my_student = null where b.id = ?1")
    void unassignBook(int bookId);
}
