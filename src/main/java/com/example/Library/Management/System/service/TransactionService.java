package com.example.Library.Management.System.service;


import com.example.Library.Management.System.DTOs.responseDTOs.IssueBookResponse;
import com.example.Library.Management.System.Enums.TransactionStatus;
import com.example.Library.Management.System.exception.BookNotFoundException;
import com.example.Library.Management.System.exception.StudentNotFoundException;
import com.example.Library.Management.System.model.Book;
import com.example.Library.Management.System.model.Student;
import com.example.Library.Management.System.model.Transaction;
import com.example.Library.Management.System.repository.BookRepository;
import com.example.Library.Management.System.repository.StudentRepository;
import com.example.Library.Management.System.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;


@Service
public class TransactionService {

    final TransactionRepository transactionRepository;

    final BookRepository bookRepository;

    final StudentRepository studentRepository;

    @Autowired
    public TransactionService(TransactionRepository transactionRepository,
                              BookRepository bookRepository,
                              StudentRepository studentRepository) {

        this.transactionRepository = transactionRepository;
        this.bookRepository = bookRepository;
        this.studentRepository = studentRepository;
    }


    public IssueBookResponse issueBook(int bookId, int studentId) {

        // check if Student present
        Optional<Student> studentOptional= studentRepository.findById(studentId);
        if(studentOptional.isEmpty()){
            throw new StudentNotFoundException("Invalid Stident Id!");
        }

        // check if book present
        Optional<Book> bookOptional= bookRepository.findById(bookId);
        if(bookOptional.isEmpty()){
            throw new BookNotFoundException("Invalid Book Id!");
        }

        Book book= bookOptional.get();

        // check if book available for issue
        if(book.isIssued()){
            throw new BookNotFoundException("Book already issued!");
        }

        // issue book

        Student student= studentOptional.get();

        // crate transaction
        Transaction transaction= Transaction.builder()
                .book(book)
                .transactionStatus(TransactionStatus.SUCCESS)
                .transactionId(UUID.randomUUID().toString())
                .libraryCard(student.getLibraryCard())
                .build();

        Transaction savedTransaction= transactionRepository.save(transaction);

        // update book
        book.setIssued(true);
        book.getTransactions().add(savedTransaction);

        // update Library card
        student.getLibraryCard().getTransactions().add(savedTransaction);

        // save book and student
        Book savedBook= bookRepository.save(book);
        Student savedStudent= studentRepository.save(student);

        // Crate the response

        IssueBookResponse response= IssueBookResponse.builder()
                .bookName(savedBook.getTitle())
                .authorName(savedBook.getAuthor().getName())
                .libraryCardNumber(savedStudent.getLibraryCard().getCardId())
                .studentName(savedStudent.getName())
                .transactionNumber(savedTransaction.getTransactionId())
                .transactionStatus(savedTransaction.getTransactionStatus())
                .transactionTime(savedTransaction.getTransactionTime())
                .build();


        return response;
    }
}
