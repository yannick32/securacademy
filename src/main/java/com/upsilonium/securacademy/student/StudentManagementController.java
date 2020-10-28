package com.upsilonium.securacademy.student;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Yannick Van Ham
 * created on Sunday, 04/10/2020
 */
@Controller
@RequestMapping("management/students")
@AllArgsConstructor
public class StudentManagementController {
    private static final String STUDENTS_VIEW = "students";
    private final StudentService studentService;

    @GetMapping()
    public String getAllStudents(Model model){
        List<Student> students = studentService.retrieveAll();
        model.addAttribute("students", students);
        return STUDENTS_VIEW;
    }
}
