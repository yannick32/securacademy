package com.upsilonium.securacademy.course;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * @author Yannick Van Ham
 * created on Sunday, 04/10/2020
 */
@RestController
@RequestMapping("management/api/v1/courses")
@AllArgsConstructor
public class CourseManagementRestController {
    private final CourseService courseService;
    private static final String COURSES_VIEW = "courses";

    @GetMapping
    public String getAllCourses(){
        return COURSES_VIEW;
    }

    @PostMapping
    public void registerNewCourse(@RequestBody Course course){
        courseService.save(course);
    }

    @DeleteMapping("{courseId}")
    public void deleteStudent(@PathVariable("courseId") Long id){
        courseService.delete(id);
    }

    @PutMapping("{courseId}")
    public void updateStudent(@PathVariable("courseId") Long id, @RequestBody Course course){
        courseService.update(id, course);
    }
}
