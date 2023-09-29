package com.example.Library.Management.System.controller;

import com.example.Library.Management.System.exception.AuthorNotFoundException;
import com.example.Library.Management.System.model.Author;
import com.example.Library.Management.System.model.Book;
import com.example.Library.Management.System.service.AuthorService;
import com.example.Library.Management.System.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/book")
public class BookController {
    @Autowired
    BookService bookService;


    @PostMapping("/add")
    public ResponseEntity addBook(@RequestBody Book book ){

        try {
             String response = bookService.addBook(book);
            return new ResponseEntity(response, HttpStatus.CREATED);

        }
        catch(AuthorNotFoundException e){
            String response= e.getMessage();
            return new ResponseEntity(response, HttpStatus.EXPECTATION_FAILED);
        }

    }



}
