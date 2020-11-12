package com.upsilonium.securacademy.course;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Yannick Van Ham
 * created on Sunday, 04/10/2020
 */
@RestController
@RequestMapping("management/api/v1/courses")
@AllArgsConstructor
public class CourseManagementRestController {
    private final CourseService courseService;

    @GetMapping
    public List<Course> getAllCourses(){
        return courseService.retrieveAll();
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
