package com.example.Library.Management.System.controller;

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
    public ResponseEntity addStudent(@RequestBody Student student){

        return studentService.addStudent(student);
        //return "Student added Successfully";

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

}
