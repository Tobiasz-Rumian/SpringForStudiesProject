package com.klima.projekt1.user.controller;

import com.klima.projekt1.user.enums.Role;
import com.klima.projekt1.user.model.entity.User;
import com.klima.projekt1.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.Map;

@Controller
public class RegisterController {
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private UserService userService;

    @Autowired
    public RegisterController(BCryptPasswordEncoder bCryptPasswordEncoder, UserService userService) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.userService = userService;
    }

    @GetMapping("/register")
    public String showRegistrationPage(Model model) {
        model.addAttribute("user", userService.getUser());
        return "register";
    }

    @PostMapping("/register")
    public String processRegistrationForm(Model model, @Valid User user, BindingResult bindingResult, HttpServletRequest request, @RequestParam Map requestParams, RedirectAttributes redir) {
        user.setRole(Role.USER);
        user.setMoney(new BigDecimal(0));
        boolean userExists = userService.checkIfUserExists(user.getEmail());
        System.out.println(userExists);
        if (userExists) {
            model.addAttribute("alreadyRegisteredMessage", "Konto dla tego adresu email już istnieje");
            bindingResult.reject("email");
        }
        if (bindingResult.getErrorCount() > 0) {
            StringBuilder sb = new StringBuilder();
            for (ObjectError e:bindingResult.getAllErrors()) sb.append(e.getObjectName()).append("\n");
            model.addAttribute("errorMessage", sb);
        }
        model.addAttribute("confirmationMessage", "User created");
        user.setPassword(bCryptPasswordEncoder.encode((CharSequence) requestParams.get("password")));
        user.setEnabled(true);
        userService.saveUser(user);
        model.addAttribute("successMessage", "Your password has been set!");
        model.addAttribute("user", userService.getUser());
        return "register";
    }

}
