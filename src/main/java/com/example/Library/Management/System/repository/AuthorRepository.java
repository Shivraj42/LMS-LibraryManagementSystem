package com.example.Library.Management.System.repository;

import com.example.Library.Management.System.DTOs.responseDTOs.AuthorResponse;
import com.example.Library.Management.System.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Integer> {

    @Query(value ="select a from Author a where size(a.books)>:x")
    public List<Author> getAuthorWithMoreThanXBooks(int x);
}
