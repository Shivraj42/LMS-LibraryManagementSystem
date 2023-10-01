package com.example.Library.Management.System.DTOs.responseDTOs;

import com.example.Library.Management.System.Enums.CardStatus;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class LibraryCardResponse {

    String cardNo;

    CardStatus cardStatus;

    Date issueDate;

}
