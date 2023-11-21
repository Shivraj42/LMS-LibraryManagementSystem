package com.example.Library.Management.System.exception;

public class CardNotActiveException extends RuntimeException{
    public CardNotActiveException(String message){
        super(message);
    }
}
