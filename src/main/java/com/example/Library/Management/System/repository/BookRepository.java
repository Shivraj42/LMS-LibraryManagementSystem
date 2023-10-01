package com.example.Library.Management.System.repository;
import com.example.Library.Management.System.Enums.Genre;
import com.example.Library.Management.System.model.Author;
import com.example.Library.Management.System.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Integer>{

    List<Book> findByGenre(Genre genre);

    @Query(value = "select b from Book b where b.genre=:genre and b.cost>:x")
    List<Book> findBookByGenreAndCostMoreThanX(Genre genre, double x);

    @Query(value = "select b from Book b where b.noOfPages>:a and b.noOfPages<:b")
    List<Book> findBooksHavingPagesBetweenAAndB(int a, int b);

    @Query(value = "select distinct b.author from Book b where b.genre=:genre")
    List<Author> findAllAuthorsWithGenreX(Genre genre);
}
