package com.klima.projekt1.user.controller;

import com.klima.projekt1.user.model.entity.User;
import com.klima.projekt1.user.service.UserService;
import com.nulabinc.zxcvbn.Strength;
import com.nulabinc.zxcvbn.Zxcvbn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
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
    public String showRegistrationPage(Model model, User user) {
        model.addAttribute("user", user);
        return "register";
    }

    @PostMapping("/register")
    public String processRegistrationForm(Model model, @Valid User user, BindingResult bindingResult, HttpServletRequest request, @RequestParam Map requestParams, RedirectAttributes redir) {
        boolean userExists = userService.checkIfUserExists(user.getEmail());
        System.out.println(userExists);
        if (userExists) {
            model.addAttribute("alreadyRegisteredMessage", "Oops!  There is already a user registered with the email provided.");
            bindingResult.reject("email");
        }
        if (!bindingResult.hasErrors()) {
            model.addAttribute("confirmationMessage", "User created");

            Zxcvbn passwordCheck = new Zxcvbn();
            Strength strength = passwordCheck.measure((String) requestParams.get("password"));
            if (strength.getScore() < 3) {
                bindingResult.reject("password");
                redir.addFlashAttribute("errorMessage", "Your password is too weak.  Choose a stronger one.");
                System.out.println(requestParams.get("token"));
                return "redirect:confirm?token=" + requestParams.get("token");
            }

            user.setPassword(bCryptPasswordEncoder.encode((CharSequence) requestParams.get("password")));
            user.setEnabled(true);
            userService.saveUser(user);

            model.addAttribute("successMessage", "Your password has been set!");

        }
        return "register";
    }

}
