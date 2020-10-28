package com.upsilonium.securacademy.course;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Yannick Van Ham
 * created on Sunday, 04/10/2020
 */
@Getter
@Setter
@AllArgsConstructor
public class Course {
    private Long id;
    private String name;
    private String instructor;
    private Integer credits;
}
