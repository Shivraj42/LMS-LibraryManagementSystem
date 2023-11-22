package com.example.Library.Management.System.controller;

import com.example.Library.Management.System.DTOs.requestDTOs.StudentRequest;
import com.example.Library.Management.System.DTOs.responseDTOs.BookResponse;
import com.example.Library.Management.System.DTOs.responseDTOs.StudentResponse;
import com.example.Library.Management.System.exception.StudentNotFoundException;
import com.example.Library.Management.System.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
        try {
            StudentResponse studentResponse= studentService.getStudent(regNo);
            return new ResponseEntity(studentResponse, HttpStatus.FOUND);
        }
        catch (StudentNotFoundException e){
            String response = e.getMessage();
            return new ResponseEntity(response, HttpStatus.NOT_FOUND);
        }


    }
    // delete a student --> regNo
    @DeleteMapping("/delete/{regNo}")
    public ResponseEntity deleteByRegNo(@PathVariable int regNo){
        try {
            String response = studentService.deleteByRegNo(regNo);
            return new ResponseEntity(response, HttpStatus.ACCEPTED);
        }
        catch (StudentNotFoundException e){
            String response= e.getMessage();
            return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
        }
    }

    // update the age of a student  ---> regNo, age
    @PutMapping("/update/age/{regNo}")
    public ResponseEntity updateAge(@PathVariable int regNo, @RequestParam int newAge){
        try {
            String response= studentService.updateAge(regNo, newAge);
            return new ResponseEntity(response, HttpStatus.CREATED);
        }
        catch (StudentNotFoundException e){
            String response= e.getMessage();
            return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
        }
    }

    // get all books that student have currently
    @GetMapping("/get/all_books")
    public ResponseEntity getAllBooksOnAccount(@RequestParam int regNo){
        try{
            List<BookResponse> response= studentService.getAllBooksOnAccount(regNo);
            return new ResponseEntity(response, HttpStatus.FOUND);
        }
        catch (RuntimeException e){
            String response= e.getMessage();
            return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
        }
    }

    // get all the students in the db  --> findAll()
    @GetMapping("/get/all")
    public ResponseEntity getAllStudent(){
        List<StudentResponse> students= studentService.getAllStudent();
        return new ResponseEntity(students, HttpStatus.FOUND);
    }

    // get list of all male students
    @GetMapping("/get/all/male")
    public ResponseEntity getAllMaleStudent() {
        try {
            List<StudentResponse> students = studentService.getAllMaleStudent();
            return new ResponseEntity(students, HttpStatus.FOUND);
        }
        catch (StudentNotFoundException e){
            String response= e.getMessage();
            return new ResponseEntity(response, HttpStatus.NOT_FOUND);
        }
    }


}
