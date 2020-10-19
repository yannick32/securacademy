package com.upsilonium.securacademy.security;

import com.upsilonium.securacademy.auth.ApplicationUserService;
import com.upsilonium.securacademy.jwt.JwtConfig;
import com.upsilonium.securacademy.jwt.JwtTokenVerifier;
import com.upsilonium.securacademy.jwt.JwtUsernameAndPasswordAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import javax.crypto.SecretKey;
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
        private final ApplicationUserService applicationUserService;

        public FormBasedAuthSecurityConfig(PasswordEncoder passwordEncoder,
                                           ApplicationUserService applicationUserService) {
            this.passwordEncoder = passwordEncoder;
            this.applicationUserService = applicationUserService;
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

        @Bean
        public DaoAuthenticationProvider daoAuthenticationProvider(){
            DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
            provider.setPasswordEncoder(passwordEncoder);
            provider.setUserDetailsService(applicationUserService);
            return provider;
        }

        @Override
        protected void configure(AuthenticationManagerBuilder auth) throws Exception {
            auth.authenticationProvider(daoAuthenticationProvider());
        }
    }

    @Configuration
    @Profile("jwtAuth")
    public class JwtAuthSecurityConfig extends WebSecurityConfigurerAdapter {
        private final PasswordEncoder passwordEncoder;
        private final ApplicationUserService applicationUserService;
        private final SecretKey secretKey;
        private final JwtConfig jwtConfig;

        public JwtAuthSecurityConfig(PasswordEncoder passwordEncoder,
                                     ApplicationUserService applicationUserService, SecretKey secretKey,
                                     JwtConfig jwtConfig) {
            this.passwordEncoder = passwordEncoder;
            this.applicationUserService = applicationUserService;
            this.secretKey = secretKey;
            this.jwtConfig = jwtConfig;
        }

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http
                    .csrf().disable()
                    .sessionManagement()
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                    .and()
                    .addFilter(new JwtUsernameAndPasswordAuthenticationFilter(authenticationManager(), jwtConfig,
                            secretKey))
                    .addFilterAfter(new JwtTokenVerifier(secretKey, jwtConfig), JwtUsernameAndPasswordAuthenticationFilter.class)
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
                    .authenticated();
        }

        @Bean
        public DaoAuthenticationProvider daoAuthenticationProvider(){
            DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
            provider.setPasswordEncoder(passwordEncoder);
            provider.setUserDetailsService(applicationUserService);
            return provider;
        }

        @Override
        protected void configure(AuthenticationManagerBuilder auth) throws Exception {
            auth.authenticationProvider(daoAuthenticationProvider());
        }
    }


}
