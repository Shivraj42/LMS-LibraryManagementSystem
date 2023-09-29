package com.example.Library.Management.System.service;

import com.example.Library.Management.System.model.Author;
import com.example.Library.Management.System.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthorService {
    @Autowired
    AuthorRepository authorRepository;

    public String addAuthor(Author author) {
        authorRepository.save(author);
        return "Author Saved Succesfully!";
    }
}
