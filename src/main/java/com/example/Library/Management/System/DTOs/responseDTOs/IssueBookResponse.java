package com.example.Library.Management.System.DTOs.responseDTOs;

import com.example.Library.Management.System.Enums.TransactionStatus;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class IssueBookResponse {
    String transactionNumber;

    Date transactionTime;

    TransactionStatus transactionStatus;

    String bookName;

    String authorName;

    String studentName;

    String libraryCardNumber;

}
