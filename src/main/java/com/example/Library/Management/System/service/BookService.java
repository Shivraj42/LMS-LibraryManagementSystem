package com.example.Library.Management.System.service;

import com.example.Library.Management.System.exception.AuthorNotFoundException;
import com.example.Library.Management.System.model.Author;
import com.example.Library.Management.System.model.Book;
import com.example.Library.Management.System.repository.AuthorRepository;
import com.example.Library.Management.System.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Optional;

@Service
public class BookService {
    @Autowired
    BookRepository bookRepository;

    @Autowired
    AuthorRepository authorRepository;



    public String addBook(Book book) {
        Optional<Author> authorOptional=authorRepository.findById(book.getAuthor().getId());
        if(authorOptional.isEmpty()){
            throw new AuthorNotFoundException("Invalid Author Id!");
        }
        Author author= authorOptional.get();
        book.setAuthor(author);
        author.getBooks().add(book);

        authorRepository.save(author);   // ths will save both book and author as it is cascaded
        return "Book Saved Successfully!";
    }
}
