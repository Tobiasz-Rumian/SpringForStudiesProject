package com.rumian.projekt1.exception.controller;

import com.rumian.projekt1.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class ErrorController {
    private UserService userService;

    @Autowired
    public ErrorController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/error/{userId}")
    public String getErrorForRegisteredUser(@PathVariable("userId") long userId, Model model) {
        model.addAttribute("user", userService.getUser(userId));
        return "error";
    }

    @GetMapping("/error}")
    public String getError(Model model) {
        return "error";
    }
}
