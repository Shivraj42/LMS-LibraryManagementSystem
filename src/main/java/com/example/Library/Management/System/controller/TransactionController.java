package com.example.Library.Management.System.controller;

import com.example.Library.Management.System.DTOs.responseDTOs.IssueBookResponse;
import com.example.Library.Management.System.DTOs.responseDTOs.ReturnBookResponse;
import com.example.Library.Management.System.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/transaction")
public class TransactionController {

    final TransactionService transactionService;
    @Autowired
    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping("/issue/book-id/{bookId}/student-id/{studentId}")
    public ResponseEntity issueBook(@PathVariable("bookId") int bookId,
                                    @PathVariable("studentId") int studentId){
        try{
            IssueBookResponse response= transactionService.issueBook(bookId, studentId);
            return new ResponseEntity(response, HttpStatus.CREATED);
        }
        catch (Exception e){
            String response= e.getMessage();
            return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
        }
    }
    @PostMapping("/return/book-id/{bookId}/student-id/{studentId}")
    public ResponseEntity returnBook(@PathVariable("bookId") int bookId,
                                    @PathVariable("studentId") int studentId){
        try{
            ReturnBookResponse response= transactionService.returnBook(bookId, studentId);
            return new ResponseEntity(response, HttpStatus.CREATED);
        }
        catch (Exception e){
            String response= e.getMessage();
            return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
        }
    }

}
