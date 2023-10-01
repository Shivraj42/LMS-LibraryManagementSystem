package com.example.Library.Management.System.transformers;

import com.example.Library.Management.System.DTOs.requestDTOs.StudentRequest;
import com.example.Library.Management.System.DTOs.responseDTOs.LibraryCardResponse;
import com.example.Library.Management.System.DTOs.responseDTOs.StudentResponse;
import com.example.Library.Management.System.model.Student;

public class StudentTransformer {

    public static Student StudentRequestToStudent (StudentRequest studentRequest){
        return Student.builder()
                .name(studentRequest.getName())
                .email(studentRequest.getEmail())
                .age(studentRequest.getAge())
                .gender(studentRequest.getGender())
                .build();
    }

    public static StudentResponse StudentToStudentResponse(Student student){
        LibraryCardResponse libraryCardResponse=LibraryCardTransformer.StudentToLibraryCardResponse(student);
        return StudentResponse.builder()
                .name(student.getName())
                .email(student.getEmail())
                .libraryCardResponse(libraryCardResponse)
                .build();
    }
}
