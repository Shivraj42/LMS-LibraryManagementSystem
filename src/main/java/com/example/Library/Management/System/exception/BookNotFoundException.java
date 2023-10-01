package com.example.Library.Management.System.exception;

public class BookNotFoundException extends RuntimeException{

    public BookNotFoundException(String message){
        super(message);
    }
}
