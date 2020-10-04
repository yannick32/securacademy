package com.upsilonium.securacademy.student;

import org.springframework.stereotype.Service;

/**
 * @author Yannick Van Ham
 * created on Sunday, 04/10/2020
 */
@Service
public class StudentServiceImpl implements StudentService {
    private final StudentRepository studentRepository;

    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public Student findStudentById(Long id) {
        return studentRepository.findById(id);
    }
}
