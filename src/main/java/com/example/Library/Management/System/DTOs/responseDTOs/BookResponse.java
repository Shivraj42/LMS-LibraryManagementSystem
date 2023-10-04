package com.example.Library.Management.System.DTOs.responseDTOs;

import com.example.Library.Management.System.Enums.Genre;
import lombok.*;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class BookResponse {

    String title;

    Genre genre;

    String authorName;

    int noOfPages;

    Double cost;

}
