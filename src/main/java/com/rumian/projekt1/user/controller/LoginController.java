package com.rumian.projekt1.user.controller;

import com.rumian.projekt1.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LoginController {
    private UserService userService;

    @Autowired
    public LoginController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = {"/login"}, method = RequestMethod.GET)
    public ModelAndView login() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("user", userService.getUser());
        modelAndView.setViewName("login");
        return modelAndView;
    }

    @GetMapping("/login_error")
    public String loginError(Model model) {
        model.addAttribute("user", userService.getUser());
        model.addAttribute("loginError", true);
        return "login";
    }
}
