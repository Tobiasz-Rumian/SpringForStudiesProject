package com.klima.projekt1.user.controller;

import com.klima.projekt1.notification.model.enums.NotificationCode;
import com.klima.projekt1.notification.service.NotificationService;
import com.klima.projekt1.user.enums.Role;
import com.klima.projekt1.user.model.entity.User;
import com.klima.projekt1.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.time.ZonedDateTime;
import java.util.Map;

@Controller
public class UserController {
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private UserService userService;
    private NotificationService notificationService;
    @Autowired
    public UserController(BCryptPasswordEncoder bCryptPasswordEncoder, UserService userService, NotificationService notificationService) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.userService = userService;
        this.notificationService = notificationService;
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

    @GetMapping("/user_account/{id}")
    public String getAdminConsole(@PathVariable("id") long id, Model model) {
        model.addAttribute("user", userService.getUser(id));
        model.addAttribute("unreadNotificationNumber", notificationService.getNumberOfUnreedUserNotifications(id));
        return "user_account";
    }

    @PostMapping("/user_account/{userId}")
    public String processChangeUserDataForm(@PathVariable("userId") long userId, Model model, @Valid User user, BindingResult bindingResult, HttpServletRequest request, @RequestParam Map requestParams, RedirectAttributes redir) {
        if (bindingResult.getErrorCount() > 1) {
            StringBuilder sb = new StringBuilder();
            for (ObjectError e : bindingResult.getAllErrors()) sb.append(e.getObjectName()).append("\n");
            model.addAttribute("errorMessage", sb);
        }
        User user1 = userService.getUserByEmail(user.getEmail());
        user1.setFirstName(user.getFirstName());
        user1.setLastName(user.getLastName());
        user1.setPesel(user.getPesel());
        user1.getAddress().setPhoneNumber(user.getAddress().getPhoneNumber());
        user1.setEmail(user.getEmail());
        if (requestParams.containsKey("passChange") && requestParams.get("passChange").equals("true"))
            user1.setPassword(bCryptPasswordEncoder.encode((CharSequence) requestParams.get("password")));
        userService.saveUser(user1);
        notificationService.addNotification(NotificationCode.USER_CHANGED_PERSONAL_DATA, user1);
        model.addAttribute("user", user1);
        return "user_account";
    }

    @PostMapping("/user_account_delete/{userId}")
    public String processDeleteUserForm(@PathVariable("userId") long userId, Model model, BindingResult bindingResult, HttpServletRequest request, @RequestParam Map requestParams, RedirectAttributes redir) {
        User user = userService.getUser(userId);
        if (user.getOffer() != null
                || user.getPayDate().isBefore(ZonedDateTime.now())
                || user.getRole() == Role.ADMIN) {
            model.addAttribute("message", "Użytkownik nie może zostać usunięty");
            return "user_account";
        }
        userService.deleteUser(user);
        return "/logout";
    }
}
