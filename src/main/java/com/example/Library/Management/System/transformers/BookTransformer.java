package com.example.Library.Management.System.transformers;

import com.example.Library.Management.System.DTOs.requestDTOs.BookRequest;
import com.example.Library.Management.System.DTOs.responseDTOs.BookResponse;
import com.example.Library.Management.System.model.Book;

public class BookTransformer {
    public static Book BookRequestToBook(BookRequest bookRequest){
        return Book.builder()
                .title(bookRequest.getTitle())
                .noOfPages(bookRequest.getNoOfPages())
                .genre(bookRequest.getGenre())
                .cost(bookRequest.getCost())
                .build();
    }
    public static BookResponse BookToBookResponse(Book book){
        return BookResponse.builder()
                .title(book.getTitle())
                .genre(book.getGenre())
                .authorName(book.getAuthor().getName())
                .cost(book.getCost())
                .noOfPages(book.getNoOfPages())
                .build();
    }
}
