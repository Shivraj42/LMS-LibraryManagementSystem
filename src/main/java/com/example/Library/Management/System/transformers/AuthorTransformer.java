package com.example.Library.Management.System.transformers;

import com.example.Library.Management.System.DTOs.requestDTOs.AuthorRequest;
import com.example.Library.Management.System.DTOs.responseDTOs.AuthorResponse;
import com.example.Library.Management.System.model.Author;

public class AuthorTransformer {

    public  static Author AuthorRequestToAuthor(AuthorRequest authorRequest){
        return Author.builder()
                .age(authorRequest.getAge())
                .email(authorRequest.getEmail())
                .name(authorRequest.getName())
                .build();
    }
    public static AuthorResponse AuthorToAuthorResponse(Author author){
        return AuthorResponse.builder()
                .name(author.getName())
                .email(author.getEmail())
                .build();
    }
}
