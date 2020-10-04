package com.upsilonium.securacademy.security;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author Yannick Van Ham
 * created on Sunday, 04/10/2020
 */
@Getter
@RequiredArgsConstructor
public enum UserPermission {
    STUDENT_READ("student:read"),
    STUDENT_WRITE("student:write"),
    COURSE_READ("course:read"),
    COURSE_WRITE("course:write");

    private final String permission;
}
