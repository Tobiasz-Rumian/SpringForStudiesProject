package com.rumian.projekt1.user.controller;

import com.rumian.projekt1.exception.controller.ErrorController;
import com.rumian.projekt1.user.model.entity.User;
import com.rumian.projekt1.user.service.RegisterService;
import com.rumian.projekt1.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

import javax.validation.Valid;
import java.util.Map;

@Controller
public class RegisterController {
    private UserService userService;
    private RegisterService registerService;
    private ErrorController errorController;

    @Autowired
    public RegisterController(UserService userService,
                              RegisterService registerService,
                              ErrorController errorController) {
        this.userService = userService;
        this.registerService = registerService;
        this.errorController = errorController;
    }

    @GetMapping("/register")
    public String showRegistrationPage(Model model) {
        model.addAttribute("user", userService.getUser());
        return "register";
    }

    @PostMapping("/register")
    public RedirectView processRegistrationForm(Model model,
                                                @Valid User user,
                                                BindingResult bindingResult,
                                                @RequestParam Map requestParams) {
        Map<String, String> results = registerService
                .registerUser(user, bindingResult, (String) requestParams.get("password"));
        if (results.containsKey("fail")) {
            if (results.containsKey("alreadyRegistered")) {
                model.addAttribute("message", "Konto dla tego adresu email już istnieje");
            }
            if (results.containsKey("errors")) model.addAttribute("message", results.get("errors"));
            return new RedirectView(errorController.getError(model));
        } else if (results.containsKey("success")) {
            model.addAttribute("confirmationMessage", "Użytkownik został zarejestrowany.");
            model.addAttribute("user", userService.getUser());
            return new RedirectView("/");
        } else throw new IllegalStateException("Both keys not found!");
    }
}
