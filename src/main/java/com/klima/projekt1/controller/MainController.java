package com.klima.projekt1.controller;

import org.springframework.stereotype.Controller;
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

//   @GetMapping("/login")
//   public String getLogin() {
//      return "login";
//   }

   @GetMapping("/console")
   public String getCmd() {
      return "console";
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

   @GetMapping("/user_facture")
   public String getUserFacture() {
      return "user_facture";
   }

   @GetMapping("/user_account")
   public String getUserAccount() {
      return "user_account";
   }
}
