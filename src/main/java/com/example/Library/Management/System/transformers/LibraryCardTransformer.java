package com.example.Library.Management.System.transformers;

import com.example.Library.Management.System.DTOs.responseDTOs.LibraryCardResponse;
import com.example.Library.Management.System.Enums.CardStatus;
import com.example.Library.Management.System.model.LibraryCard;
import com.example.Library.Management.System.model.Student;

import java.util.ArrayList;
import java.util.UUID;

public class LibraryCardTransformer {

    public static LibraryCard PrepareLibraryCard(){
        return LibraryCard.builder()
                .cardStatus(CardStatus.ACTIVE)
                .issuedBooks(new ArrayList<Integer>())
                .CardId(UUID.randomUUID().toString())
                .build();
    }
    public static LibraryCardResponse StudentToLibraryCardResponse(Student student){
        return LibraryCardResponse.builder()
                .cardNo(student.getLibraryCard().getCardId())
                .cardStatus(student.getLibraryCard().getCardStatus())
                .issueDate(student.getLibraryCard().getDateOfIssue())
                .build();
    }
}
