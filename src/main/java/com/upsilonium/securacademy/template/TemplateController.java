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
    private static final String INDEX_VIEW = "index";
    private static final String ERROR_ACCESS_DENIED = "error/403";

    @GetMapping
    public String getIndexView(){
        return INDEX_VIEW;
    }

    @GetMapping("login")
    public String getLoginView(){
        return LOGIN_VIEW;
    }

    @RequestMapping("/403")
    public String accessDenied(){
        return ERROR_ACCESS_DENIED;
    }


}
