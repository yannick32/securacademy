package com.upsilonium.securacademy.course;

import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Yannick Van Ham
 * created on Sunday, 04/10/2020
 */
@Service
public class CourseServiceImpl implements CourseService {
    private final CourseRepository<Course> courseRepository;

    public CourseServiceImpl(CourseRepository<Course> courseRepository) {
        this.courseRepository = courseRepository;
    }

    @Override
    public Course findCourseById(Long id) {
        return courseRepository.findById(id);
    }

    @Override
    public List<Course> retrieveAll() {
        return courseRepository.retrieveAll();
    }

    @Override
    public void save(Course course) {
        courseRepository.save(course);
    }

    @Override
    public void delete(Long id) {
        courseRepository.delete(id);
    }

    @Override
    public void update(Long id, Course course) {
        courseRepository.update(id, course);
    }
}
