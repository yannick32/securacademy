package com.upsilonium.securacademy.course;

import com.upsilonium.securacademy.student.Student;
import com.upsilonium.securacademy.student.StudentService;
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
@RequestMapping("management/courses")
@AllArgsConstructor
public class CourseManagementController {
    private static final String COURSES_VIEW = "courses";
    private final CourseService courseService;

    @GetMapping
    public String getAllCourses(Model model) {
        List<Course> courses = courseService.retrieveAll();
        model.addAttribute("courses", courses);
        return COURSES_VIEW;
    }
}
