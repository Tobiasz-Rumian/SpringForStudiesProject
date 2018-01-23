package com.rumian.projekt1.user.controller;

import com.rumian.projekt1.notification.mapper.NotificationMapper;
import com.rumian.projekt1.notification.service.NotificationService;
import com.rumian.projekt1.user.mapper.UserMapper;
import com.rumian.projekt1.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class AdminController {
    private UserService userService;
    private UserMapper userMapper;
    private NotificationService notificationService;
    private NotificationMapper notificationMapper;

    @Autowired
    public AdminController(UserService userService, UserMapper userMapper, NotificationService notificationService, NotificationMapper notificationMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
        this.notificationService = notificationService;
        this.notificationMapper = notificationMapper;
    }

    @GetMapping("/admin_users/{id}")
    public String getAdminUsers(@PathVariable("id") long id, Model model) {
        model.addAttribute("user", userService.getUser(id));
        model.addAttribute("users", userMapper.toUserDTOs(userService.getUsers()));
        model.addAttribute("unreadNotificationNumber", notificationService.getNumberOfUnreadNotifications());
        return "admin_users";
    }

    @GetMapping("/admin_console/{id}")
    public String getAdminConsole(@PathVariable("id") long id, Model model) {
        model.addAttribute("user", userService.getUser(id));
        model.addAttribute("unreadNotificationNumber", notificationService.getNumberOfUnreadNotifications());
        model.addAttribute("pastPayDateNumber", userService.getNuberOfAllAfterPayDate());
        return "admin_console";
    }

    @GetMapping("/admin_notifications/{id}")
    public String getAdminNotifications(@PathVariable("id") long id, Model model) {
        model.addAttribute("user", userService.getUser(id));
        model.addAttribute("unreadNotificationNumber", notificationService.getNumberOfUnreadNotifications());
        model.addAttribute("notifications", notificationMapper.toNotificationDtos(notificationService.getAllUnreedNotifications()));
        return "admin_notifications";
    }
}
