package com.example.Library.Management.System.repository;

import com.example.Library.Management.System.Enums.TransactionType;
import com.example.Library.Management.System.model.Book;
import com.example.Library.Management.System.model.LibraryCard;
import com.example.Library.Management.System.model.Student;
import com.example.Library.Management.System.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Integer> {

    @Query(value = "SELECT t\n" +
            "FROM Transaction t\n" +
            "WHERE t.book = :book\n" +
            "  AND t.libraryCard = :libraryCard\n" +
            "  AND t.transactionType = :transactionType\n")
    public List<Transaction> findByBookAndStudent(Book book, LibraryCard libraryCard, TransactionType transactionType);

}
