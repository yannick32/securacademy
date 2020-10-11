package com.upsilonium.securacademy.template;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Yannick Van Ham
 * created on Sunday, 11/10/2020
 */
@Controller
@RequestMapping("/")
public class TemplateController {
    private static final String LOGIN_VIEW = "auth/login";
    private static final String COURSES_VIEW = "courses";

    @GetMapping("login")
    public String getLoginView(){
        return LOGIN_VIEW;
    }

    @GetMapping("courses")
    public String getCoursesView(){
        return COURSES_VIEW;
    }


}
