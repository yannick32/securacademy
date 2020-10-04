package com.upsilonium.securacademy.student;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

/**
 * @author Yannick Van Ham
 * created on Sunday, 04/10/2020
 */
@Getter
@Setter
@RequiredArgsConstructor
public class Student {
    private final Long id;
    private final String name;
}
