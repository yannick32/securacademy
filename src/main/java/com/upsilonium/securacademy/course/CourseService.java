package com.upsilonium.securacademy.course;

import java.util.List;

/**
 * @author Yannick Van Ham
 * created on Sunday, 04/10/2020
 */
public interface CourseService {
    Course findCourseById(Long id);

    List<Course> retrieveAll();

    void save(Course course);

    void delete(Long id);

    void update(Long id, Course course);
}
