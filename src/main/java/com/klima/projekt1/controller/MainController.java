package com.klima.projekt1.controller;

import com.klima.projekt1.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.websocket.server.PathParam;

@Controller
public class MainController {
    private UserService userService;

    @Autowired
    public MainController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public String getIndex(Model model) {
        model.addAttribute("user", userService.getUser());
        return "index";
    }

    @GetMapping("/index/{id}")
    public String getIndex(@PathParam("id") long id, Model model) {
        model.addAttribute("user", userService.getUser(id));
        return "index";
    }


    @GetMapping("/admin_console/{id}")
    public String getAdminConsole(@PathParam("id") long id, Model model) {
        model.addAttribute("user", userService.getUser(id));
        return "admin_console";
    }

    @GetMapping("/contact/{id}")
    public String getContact(@PathParam("id") long id, Model model) {
        model.addAttribute("user", userService.getUser(id));
        return "contact";
    }

    @GetMapping("/user_main/{id}")
    public String getUserMain(@PathParam("id") long id, Model model) {
        model.addAttribute("user", userService.getUser(id));
        return "user_main";
    }

    @GetMapping("/user_offers/{id}")
    public String getUserOffers(@PathParam("id") long id, Model model) {
        model.addAttribute("user", userService.getUser(id));
        return "user_offers";
    }

    @GetMapping("/user_wallet/{id}")
    public String getUserWallet(@PathParam("id") long id, Model model) {
        model.addAttribute("user", userService.getUser(id));

        return "user_wallet";
    }

    @GetMapping("/user_invoice/{id}")
    public String getUserInvoice(@PathParam("id") long id, Model model) {
        model.addAttribute("user", userService.getUser(id));
        return "user_invoice";
    }

    @GetMapping("/user_account/{id}")
    public String getUserAccount(@PathParam("id") long id, Model model) {
        model.addAttribute("user", userService.getUser(id));
        return "user_account";
    }
}
