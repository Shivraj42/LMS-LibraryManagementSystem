package com.example.Library.Management.System.controller;

import com.example.Library.Management.System.DTOs.requestDTOs.AuthorRequest;
import com.example.Library.Management.System.DTOs.responseDTOs.AuthorResponse;
import com.example.Library.Management.System.DTOs.responseDTOs.BookResponse;
import com.example.Library.Management.System.exception.AuthorNotFoundException;
import com.example.Library.Management.System.exception.BookNotFoundException;
import com.example.Library.Management.System.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/author")
public class AuthorController {
    @Autowired
    AuthorService authorService;

    @PostMapping("/add")
    public ResponseEntity addAuthor(@RequestBody AuthorRequest authorRequest){
        AuthorResponse authorResponse= authorService.addAuthor(authorRequest);
        return new ResponseEntity(authorResponse, HttpStatus.CREATED);
    }

    // update the email id of an author  -->> observer lastActivity column
    @PutMapping("/update-email")
    public ResponseEntity updateEmail(@RequestParam int id, String newEmail){
        try {
            AuthorResponse authorResponse = authorService.updateEmail(id, newEmail);
            return new ResponseEntity(authorResponse, HttpStatus.CREATED);
        }
        catch(AuthorNotFoundException e){
            String response= e.getMessage();
            return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
        }
    }

    // Give me the names of all the books written by a partiular author

    @GetMapping("/all-books-written-by-author")
    public ResponseEntity getAllBookWrittenByX(@RequestParam int id){

        try {
            List<BookResponse> response = authorService.getAllBookWrittenByX(id);
            return new ResponseEntity(response, HttpStatus.FOUND);
        }
        catch (AuthorNotFoundException e){
            String response= e.getMessage();
            return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
        }
        catch (BookNotFoundException e){
            String response= e.getMessage();
            return new ResponseEntity(response, HttpStatus.NOT_FOUND);
        }
    }

    // give me the names of authors who have written more than 'x' number of books
    @GetMapping("/get-author-with-more-than-x-books")
    public ResponseEntity getAuthorWithMoreThanXBooks(@RequestParam int x){
        List<AuthorResponse> response = authorService.getAuthorWithMoreThanXBooks(x);
        return new ResponseEntity(response, HttpStatus.FOUND);
    }
}
