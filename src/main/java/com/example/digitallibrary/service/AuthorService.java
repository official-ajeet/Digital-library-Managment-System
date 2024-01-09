package com.example.digitallibrary.service;

import com.example.digitallibrary.models.Author;
import com.example.digitallibrary.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthorService {
    @Autowired
    AuthorRepository authorRepository;

    //writing business logic if author already exist in db and write another book then dont add again by different id
    public Author createOrGet(Author author){//if author is not present then create otherwise get particular author
        Author authorFromDB = this.authorRepository.findByEmail(author.getEmail());//check is author exist
        //if author exist in db then return otherwise save the author
        if(authorFromDB != null){
            return authorFromDB;
        }
        return this.authorRepository.save(author);
    }
}
