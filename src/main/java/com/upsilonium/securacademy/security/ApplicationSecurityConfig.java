package com.upsilonium.securacademy.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

import java.util.concurrent.TimeUnit;

import static com.upsilonium.securacademy.security.UserPermission.*;
import static com.upsilonium.securacademy.security.UserRole.*;

/**
 * @author Yannick Van Ham
 * created on Sunday, 04/10/2020
 */
@EnableWebSecurity
public class ApplicationSecurityConfig {

    @Configuration
    @Profile("basicAuth")
    public class BasicAuthSecurityConfig extends WebSecurityConfigurerAdapter {
        private final PasswordEncoder passwordEncoder;

        public BasicAuthSecurityConfig(PasswordEncoder passwordEncoder) {
            this.passwordEncoder = passwordEncoder;
        }

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http
                    .csrf().disable()
                    .authorizeRequests()
                    .antMatchers("/", "/index", "/css/*", "/js/*").permitAll()
                    .antMatchers("/api/v1/students/**").hasRole(STUDENT.name())
                    .antMatchers(HttpMethod.GET, "/management/**").hasAnyAuthority(COURSE_READ.getPermission(),
                    STUDENT_READ.getPermission())
                    .antMatchers(HttpMethod.POST, "/management/**").hasAnyAuthority(COURSE_WRITE.getPermission(),
                    STUDENT_WRITE.getPermission())
                    .antMatchers(HttpMethod.PUT, "/management/**").hasAnyAuthority(COURSE_WRITE.getPermission(),
                    STUDENT_WRITE.getPermission())
                    .antMatchers(HttpMethod.DELETE, "/management/**").hasAnyAuthority(COURSE_WRITE.getPermission(),
                    STUDENT_WRITE.getPermission())
                    .anyRequest()
                    .authenticated()
                    .and()
                    .httpBasic();
        }

        @Override
        @Bean
        protected UserDetailsService userDetailsService() {
            UserDetails richieUser = User.builder()
                    .username("richie")
                    .password(passwordEncoder.encode("password"))
//                    .roles(STUDENT.name())
                    .authorities(STUDENT.getGrantedAuthorities())
                    .build();

            UserDetails lindaUser = User.builder()
                    .username("linda")
                    .password(passwordEncoder.encode("password"))
//                    .roles(ADMIN.name())
                    .authorities(ADMIN.getGrantedAuthorities())
                    .build();

            UserDetails karenUser = User.builder()
                    .username("karen")
                    .password(passwordEncoder.encode("password"))
//                    .roles(ADMIN_TRAINEE.name())
                    .authorities(ADMIN_TRAINEE.getGrantedAuthorities())
                    .build();

            return new InMemoryUserDetailsManager(
                    richieUser,
                    lindaUser,
                    karenUser
            );
        }
    }

    @Configuration
    @Profile("formAuth")
    public class FormBasedAuthSecurityConfig extends WebSecurityConfigurerAdapter {
        private final PasswordEncoder passwordEncoder;

        public FormBasedAuthSecurityConfig(PasswordEncoder passwordEncoder) {
            this.passwordEncoder = passwordEncoder;
        }

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http
                    .csrf().disable()
                    .authorizeRequests()
                    .antMatchers("/", "/index", "/css/*", "/js/*", "/favicon/*").permitAll()
                    .antMatchers("/api/v1/students/**").hasRole(STUDENT.name())
                    .antMatchers(HttpMethod.GET, "/management/**").hasAnyAuthority(COURSE_READ.getPermission(),
                    STUDENT_READ.getPermission())
                    .antMatchers(HttpMethod.POST, "/management/**").hasAnyAuthority(COURSE_WRITE.getPermission(),
                    STUDENT_WRITE.getPermission())
                    .antMatchers(HttpMethod.PUT, "/management/**").hasAnyAuthority(COURSE_WRITE.getPermission(),
                    STUDENT_WRITE.getPermission())
                    .antMatchers(HttpMethod.DELETE, "/management/**").hasAnyAuthority(COURSE_WRITE.getPermission(),
                    STUDENT_WRITE.getPermission())
                    .anyRequest()
                    .authenticated()
                    .and()
                    .formLogin()
                    .loginPage("/login").permitAll()
                    .defaultSuccessUrl("/courses")
                    .and()
                    .rememberMe()
                    .tokenValiditySeconds((int) TimeUnit.DAYS.toSeconds(30))
                    .rememberMeParameter("remember-me")
                    .and()
                    .logout()
                    .logoutUrl("/logout")
                    .clearAuthentication(true)
                    .invalidateHttpSession(true)
                    .deleteCookies("JSESSIONID", "remember-me")
                    .logoutSuccessUrl("/login");
        }

        @Override
        @Bean
        protected UserDetailsService userDetailsService() {
            UserDetails richieUser = User.builder()
                    .username("richie")
                    .password(passwordEncoder.encode("password"))
//                    .roles(STUDENT.name())
                    .authorities(STUDENT.getGrantedAuthorities())
                    .build();

            UserDetails lindaUser = User.builder()
                    .username("linda")
                    .password(passwordEncoder.encode("password"))
//                    .roles(ADMIN.name())
                    .authorities(ADMIN.getGrantedAuthorities())
                    .build();

            UserDetails karenUser = User.builder()
                    .username("karen")
                    .password(passwordEncoder.encode("password"))
//                    .roles(ADMIN_TRAINEE.name())
                    .authorities(ADMIN_TRAINEE.getGrantedAuthorities())
                    .build();

            return new InMemoryUserDetailsManager(
                    richieUser,
                    lindaUser,
                    karenUser
            );
        }
    }


}
