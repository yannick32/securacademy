package com.upsilonium.securacademy.student;

import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Yannick Van Ham
 * created on Sunday, 04/10/2020
 */
@RestController
@RequestMapping("management/api/v1/students")
public class StudentManagementRestController {
    private final StudentService studentService;

    public StudentManagementRestController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping()
    public List<Student> getAllStudents(){
        return studentService.retrieveAll();
    }

    @PostMapping()
    public void registerNewStudent(@RequestBody Student student){
        studentService.saveStudent(student);
    }

    @DeleteMapping("{studentId}")
    public void deleteStudent(@PathVariable("studentId") Long id){
        studentService.deleteStudent(id);
    }

    @PutMapping("{studentId}")
    public void updateStudent(@PathVariable("studentId") Long id, @RequestBody Student student){
        studentService.updateStudent(id, student);
    }
}
