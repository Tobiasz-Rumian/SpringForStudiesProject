package com.klima.projekt1.controller;

import com.klima.projekt1.offer.mapper.OfferMapper;
import com.klima.projekt1.offer.service.OfferService;
import com.klima.projekt1.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class MainController {
    private UserService userService;
    private OfferService offerService;
    private OfferMapper offerMapper;

    @Autowired
    public MainController(UserService userService, OfferService offerService, OfferMapper offerMapper) {
        this.userService = userService;
        this.offerService = offerService;
        this.offerMapper = offerMapper;
    }

    @GetMapping("/")
    public String getIndex(Model model) {
        model.addAttribute("user", userService.getUser());
        return "index";
    }

    @GetMapping("/index/{id}")
    public String getIndex(@PathVariable("id") long id, Model model) {
        model.addAttribute("user", userService.getUser(id));
        return "index";
    }


    @GetMapping("/admin_console/{id}")
    public String getAdminConsole(@PathVariable("id") long id, Model model) {
        model.addAttribute("user", userService.getUser(id));
        return "admin_console";
    }

    @GetMapping("/contact/{id}")
    public String getContact(@PathVariable("id") long id, Model model) {
        model.addAttribute("user", userService.getUser(id));
        return "contact";
    }

    @GetMapping("/user_main/{id}")
    public String getUserMain(@PathVariable("id") long id, Model model) {
        model.addAttribute("user", userService.getUser(id));
        return "user_main";
    }

    @GetMapping("/user_offers/{id}")
    public String getUserOffers(@PathVariable("id") long id, Model model) {
        model.addAttribute("user", userService.getUser(id));
        return "user_offers";
    }

    @GetMapping("/user_wallet/{id}")
    public String getUserWallet(@PathVariable("id") long id, Model model) {
        model.addAttribute("user", userService.getUser(id));

        return "user_wallet";
    }

    @GetMapping("/user_invoice/{id}")
    public String getUserInvoice(@PathVariable("id") long id, Model model) {
        model.addAttribute("user", userService.getUser(id));
        return "user_invoice";
    }

   @GetMapping("/admin_notifications")
   public String getAdminNotifications(@PathVariable("id") long id, Model model) {
       model.addAttribute("user", userService.getUser(id));
       return "admin_notifications";
   }



    @GetMapping("/user_account/{id}")
    public String getUserAccount(@PathVariable("id") long id, Model model) {
        model.addAttribute("user", userService.getUser(id));
        return "user_account";
    }
}
