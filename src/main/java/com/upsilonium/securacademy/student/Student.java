package com.upsilonium.securacademy.student;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

/**
 * @author Yannick Van Ham
 * created on Sunday, 04/10/2020
 */
@Getter
@Setter
@AllArgsConstructor
public class Student {
    private Long id;
    private String name;
}
