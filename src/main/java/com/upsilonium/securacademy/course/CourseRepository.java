package com.upsilonium.securacademy.course;

import com.upsilonium.securacademy.student.ObjectRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Yannick Van Ham
 * created on Sunday, 04/10/2020
 */
@Repository
public class CourseRepository<T> implements ObjectRepository<Course> {
    public final static List<Course> COURSES = new ArrayList<>() {
        {
            add(new Course(1L, "Java EE", "Nick Bauters", 10));
            add(new Course(2L, "Biology", "Wendy Gardner", 9));
            add(new Course(3L, "Decision Theory", "John Good", 10));
        }
    };

    @Override
    public void save(Course course) {
        COURSES.add(course);
    }

    @Override
    public Course findById(Long id) {
        return COURSES.stream()
                .filter(course -> id.equals(course.getId()))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Course " + id + " does not exist"));
    }

    @Override
    public List<Course> retrieveAll() {
        return COURSES;
    }

    @Override
    public Course delete(Long id) {
        Course course = findById(id);
        COURSES.removeIf(s -> id.equals(s.getId()));
        return course;
    }

    public Course update(Long id, Course course) {
        int indexOfStudentWithId = COURSES.indexOf(findById(id));
        COURSES.set(indexOfStudentWithId, course);
        return findById(id);
    }
}
