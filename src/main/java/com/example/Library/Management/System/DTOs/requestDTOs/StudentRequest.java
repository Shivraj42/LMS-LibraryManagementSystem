package com.example.Library.Management.System.DTOs.requestDTOs;

import com.example.Library.Management.System.Enums.Gender;
import lombok.*;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class StudentRequest {

    String name;

    int age;

    String email;

    Gender gender;
}
