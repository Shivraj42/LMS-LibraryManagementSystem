package com.example.Library.Management.System.controller;

import com.example.Library.Management.System.DTOs.requestDTOs.StudentRequest;
import com.example.Library.Management.System.DTOs.responseDTOs.StudentResponse;
import com.example.Library.Management.System.model.Student;
import com.example.Library.Management.System.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/student")
public class StudentController {
    @Autowired
    StudentService studentService;

    @PostMapping("/add")
    public ResponseEntity addStudent(@RequestBody StudentRequest studentRequest){

        StudentResponse studentResponse= studentService.addStudent(studentRequest);
        return  new ResponseEntity(studentResponse, HttpStatus.CREATED);
    }


    @GetMapping("/get")
    public ResponseEntity getStudent(@RequestParam int regNo){
        Student student= studentService.getStudent(regNo);
        if(student!=null) {
            return new ResponseEntity(student, HttpStatus.FOUND);
        }
        else{
            return  new ResponseEntity("Invalid Id!", HttpStatus.BAD_REQUEST);
        }
    }

    // delete a student --> regNo

    // update the age of a student  ---> regNo, age

    // get all the students in the db  --> findAll()

    // get list of all male students

}
