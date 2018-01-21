package com.klima.projekt1.user.controller;

import com.klima.projekt1.user.mapper.UserMapper;
import com.klima.projekt1.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class AdminUserController {
    private UserService userService;
    private UserMapper userMapper;

    @Autowired
    public AdminUserController(UserService userService, UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @GetMapping("/admin_users/{id}")
    public String getAdminUsers(@PathVariable("id") long id, Model model) {
        model.addAttribute("user", userService.getUser(id));
        model.addAttribute("users", userMapper.toUserDTOs(userService.getUsers()));
        return "admin_users";
    }
}
