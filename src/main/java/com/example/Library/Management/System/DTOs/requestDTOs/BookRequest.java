package com.example.Library.Management.System.DTOs.requestDTOs;

import com.example.Library.Management.System.Enums.Genre;
import lombok.*;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class BookRequest {

    String title;

    int noOfPages;

    Genre genre;

    Double cost;

    int authorId;
}
