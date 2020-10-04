package com.upsilonium.securacademy.student;

import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;

/**
 * @author Yannick Van Ham
 * created on Sunday, 04/10/2020
 */
@Repository
public class StudentRepository<T> implements ObjectRepository<Student> {
    public final static List<Student> STUDENTS = Arrays.asList(
            new Student(1L, "Bill Denbrough"),
            new Student(2L, "Eddie Kaspbrak"),
            new Student(3L, "Ben Hanscom"),
            new Student(4L, "Richie Tozier"),
            new Student(5L, "Stan Uris"),
            new Student(6L, "Beverly Marsh"),
            new Student(7L, "Mike Hanlon")
    );

    @Override
    public void save(Student student) {
        STUDENTS.add(student);
    }

    @Override
    public Student findById(Long id) {
        return STUDENTS.stream()
                .filter(student -> id.equals(student.getId()))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Student " + id + " does not exist"));
    }

    @Override
    public List<Student> retrieveAll() {
        return STUDENTS;
    }

    @Override
    public Student delete(Long id) {
        Student student = findById(id);
        STUDENTS.removeIf(s -> id.equals(s.getId()));
        return student;
    }

    public Student update(Long id, Student student) {
        int indexOfStudentWithId = STUDENTS.indexOf(findById(id));
        STUDENTS.set(indexOfStudentWithId, student);
        return findById(id);
    }
}
