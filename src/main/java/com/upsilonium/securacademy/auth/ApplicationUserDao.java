package com.upsilonium.securacademy.auth;

import java.util.Optional;

/**
 * @author Yannick Van Ham
 * created on Monday, 19/10/2020
 */
public interface ApplicationUserDao {
    public Optional<ApplicationUser> selectApplicationUserByUsername(String username);
}
