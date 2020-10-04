package com.upsilonium.securacademy.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import static com.upsilonium.securacademy.security.UserRole.*;

/**
 * @author Yannick Van Ham
 * created on Sunday, 04/10/2020
 */
@EnableWebSecurity
public class ApplicationSecurityConfig{

    @Configuration
    @Profile("basicAuth")
    public class BasicAuthSecurityConfig extends WebSecurityConfigurerAdapter {
        private final PasswordEncoder passwordEncoder;

        public BasicAuthSecurityConfig(PasswordEncoder passwordEncoder) {
            this.passwordEncoder = passwordEncoder;
        }

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http.authorizeRequests()
                    .antMatchers("/", "/index", "/css/*", "/js/*").permitAll()
                    .antMatchers("/api/v1/students/**").hasRole(STUDENT.name())
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
                    .roles(STUDENT.name())
                    .build();

            UserDetails lindaUser = User.builder()
                    .username("linda")
                    .password(passwordEncoder.encode("password"))
                    .roles(ADMIN.name())
                    .build();

            return new InMemoryUserDetailsManager(
                    richieUser,
                    lindaUser
            );
        }
    }


}
