package com.example.Library.Management.System.DTOs.requestDTOs;

import lombok.*;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class AuthorRequest {

    String name;

    int age;

    String email;
}
