package com.klima.projekt1.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {
   @GetMapping("/")
   public String getIndex() {
      return "index";
   }

   @GetMapping("/home")
   public String getHome() {
      return "home";
   }

   @GetMapping("/login")
   public String getLogin() {
      return "login";
   }

   @GetMapping("/login_error")
   public String loginError(Model model) {
      model.addAttribute("loginError", true);
      return "login";
   }

   @GetMapping("/admin_console")
   public String getAdminConsole() {
      return "admin_console";
   }

   @GetMapping("/register")
   public String getRegister() {
      return "register";
   }

   @GetMapping("/contact")
   public String getContact() {
      return "contact";
   }

   @GetMapping("/user_main")
   public String getUserMain() {
      return "user_main";
   }

   @GetMapping("/user_offers")
   public String getUserOffers() {
      return "user_offers";
   }

   @GetMapping("/user_wallet")
   public String getUserWallet() {
      return "user_wallet";
   }

   @GetMapping("/user_invoice")
   public String getUserInvoice() {
      return "user_invoice";
   }

   @GetMapping("/user_account")
   public String getUserAccount() {
      return "user_account";
   }

   @GetMapping("/admin_notifications")
   public String getAdminNotifications() {
      return "admin_notifications";
   }

   @GetMapping("/admin_offers")
   public String getAdminOffers() {
      return "admin_offers";
   }

   @GetMapping("/admin_users")
   public String getAdminUsers() {
      return "admin_users";
   }

   @GetMapping("/admin_addOffer")
   public String getAdminAddOffer() {
      return "admin_addOffer";
   }
}
