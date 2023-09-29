package com.example.Library.Management.System.service;

import com.example.Library.Management.System.Enums.CardStatus;
import com.example.Library.Management.System.model.LibraryCard;
import com.example.Library.Management.System.model.Student;
import com.example.Library.Management.System.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class StudentService {

    @Autowired
    StudentRepository studentRepository;
    public ResponseEntity addStudent(Student student) {
        LibraryCard libraryCard= new LibraryCard();
        libraryCard.setCardId(String.valueOf(UUID.randomUUID()));
        libraryCard.setCardStatus(CardStatus.ACTIVE);
        libraryCard.setStudent(student);
        student.setLibraryCard(libraryCard);
        Student savedStudent = studentRepository.save(student);
        return new ResponseEntity("Student Added", HttpStatus.CREATED);
    }

    public Student getStudent(int regNo) {
        Optional<Student> student= studentRepository.findById(regNo);
        if(student.isPresent()){
            return student.get();
        }
        return null;
    }

}
