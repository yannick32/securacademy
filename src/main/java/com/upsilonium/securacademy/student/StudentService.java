package com.upsilonium.securacademy.student;

import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Yannick Van Ham
 * created on Sunday, 04/10/2020
 */
public interface StudentService {
    Student findStudentById(Long id);

    List<Student> retrieveAll();

    void saveStudent(Student student);

    void deleteStudent(Long id);

    void updateStudent(Long id, Student student);
}
