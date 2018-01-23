package com.rumian.projekt1.user.controller;

import com.rumian.projekt1.notification.model.enums.NotificationCode;
import com.rumian.projekt1.notification.service.NotificationService;
import com.rumian.projekt1.user.enums.Role;
import com.rumian.projekt1.user.model.entity.User;
import com.rumian.projekt1.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.Map;

@Controller
public class UserController {
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private UserService userService;
    private NotificationService notificationService;
    @Autowired
    public UserController(BCryptPasswordEncoder bCryptPasswordEncoder,
                          UserService userService,
                          NotificationService notificationService) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.userService = userService;
        this.notificationService = notificationService;
    }

    @GetMapping("/user_account/{id}")
    public String getUserMainPage(@PathVariable("id") long id, Model model) {
        model.addAttribute("user", userService.getUser(id));
        model.addAttribute("unreadNotificationNumber", notificationService.getNumberOfUnreadNotifications());
        return "user_account";
    }

    @PostMapping("/user_account/{userId}")
    public String processChangeUserDataForm(@PathVariable("userId") long userId,
                                            Model model,
                                            @Valid User user,
                                            @RequestParam Map requestParams) {
        User user1 = userService.getUser(userId);
        if (!requestParams.containsKey("passChange")) {
            user1.setFirstName(user.getFirstName());
            user1.setLastName(user.getLastName());
            user1.setPesel(user.getPesel());
            user1.getAddress().setPhoneNumber(user.getAddress().getPhoneNumber());
            user1.setEmail(user.getEmail());
        } else if (requestParams.get("passChange").equals("true"))
            user1.setPassword(bCryptPasswordEncoder.encode((CharSequence) requestParams.get("password")));
        userService.saveUser(user1);
        notificationService.addNotification(NotificationCode.USER_CHANGED_PERSONAL_DATA, user1);
        model.addAttribute("user", user1);
        return "user_account";
    }

    @PostMapping("/user_account_delete/{userId}")
    public String processDeleteUserForm(@PathVariable("userId") long userId, Model model) {
        User user = userService.getUser(userId);
        if (user.getOffer() == null
                && (user.getPayDate() == null || !user.getPayDate().isBefore(ZonedDateTime.now()))
                && user.getRole() != Role.ADMIN) {
            userService.deleteUser(user);
            return "/logout";
        }

        model.addAttribute("message", "Użytkownik nie może zostać usunięty");
        return "user_account";
    }

    @GetMapping("/user_main/{id}")
    public String getUserMain(@PathVariable("id") long id, Model model) {
        model.addAttribute("user", userService.getUser(id));
        User user = userService.getUser(id);
        BigDecimal leftToPay = new BigDecimal(0);
        if (user.getOffer() != null && user.getPayDate().isBefore(ZonedDateTime.now()))
            leftToPay = user.getOffer().getPrice();
        model.addAttribute("leftToPay", leftToPay);
        return "user_main";
    }




}
