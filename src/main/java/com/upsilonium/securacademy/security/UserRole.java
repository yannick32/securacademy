package com.upsilonium.securacademy.security;

import com.google.common.collect.Sets;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.HashSet;
import java.util.Set;

import static com.upsilonium.securacademy.security.UserPermission.*;

/**
 * @author Yannick Van Ham
 * created on Sunday, 04/10/2020
 */
@RequiredArgsConstructor
@Getter
public enum UserRole {
    STUDENT(new HashSet<>()),
    ADMIN(Sets.newHashSet(COURSE_READ, COURSE_WRITE, STUDENT_READ, STUDENT_WRITE)),
    ADMIN_TRAINEE(Sets.newHashSet(COURSE_READ, STUDENT_READ));

    private final Set<UserPermission> permissions;
}
