package com.example.digitallibrary.repository;

import com.example.digitallibrary.models.Author;
import com.example.digitallibrary.models.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AuthorRepository extends JpaRepository<Author, Integer> {
    Author findByEmail(String email);

}
