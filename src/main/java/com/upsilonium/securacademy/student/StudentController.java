package com.upsilonium.securacademy.student;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Yannick Van Ham
 * created on Sunday, 04/10/2020
 */
@RestController
@RequestMapping("api/v1/students")
public class StudentController {
    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("{studentId}")
    public Student getStudent(@PathVariable("studentId") Long studentId){
        return studentService.findStudentById(studentId);
    }
}
