package com.klima.projekt1.user.controller;

import com.klima.projekt1.invoice.service.InvoiceService;
import com.klima.projekt1.notification.model.enums.NotificationCode;
import com.klima.projekt1.notification.service.NotificationService;
import com.klima.projekt1.offer.model.entity.Offer;
import com.klima.projekt1.offer.service.OfferService;
import com.klima.projekt1.user.enums.Role;
import com.klima.projekt1.user.model.dto.MoneyTransferDto;
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
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.Map;

@Controller
public class UserController {
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private UserService userService;
    private NotificationService notificationService;
    private OfferService offerService;
    private InvoiceService invoiceService;
    @Autowired
    public UserController(BCryptPasswordEncoder bCryptPasswordEncoder,
                          UserService userService,
                          NotificationService notificationService,
                          OfferService offerService,
                          InvoiceService invoiceService) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.userService = userService;
        this.notificationService = notificationService;
        this.offerService = offerService;
        this.invoiceService = invoiceService;
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

    @GetMapping("/user_wallet/{id}")
    public String getUserWallet(@PathVariable("id") long id, Model model) {
        model.addAttribute("user", userService.getUser(id));
        model.addAttribute("amount", new MoneyTransferDto(0));
        return "user_wallet";
    }

    @PostMapping("/user_wallet/{userId}")
    public String processUserWalletForm(@PathVariable("userId") long userId, @Valid MoneyTransferDto amount, Model model) {
        User user = userService.getUser(userId);
        if (amount.getAmount() <= 0d) {
            model.addAttribute("message", "Kwota nie może być mniejsza lub równa 0!");
            return "error";
        }
        user.setMoney(user.getMoney().add(BigDecimal.valueOf(amount.getAmount())));
        userService.saveUser(user);
        model.addAttribute("user", user);
        model.addAttribute("message", "Pomyślnie zwiększono stan konta.");
        return getUserWallet(userId, model);
    }


    @GetMapping("/user_offers/{id}")
    public String getUserOffers(@PathVariable("id") long id, Model model) {
        model.addAttribute("user", userService.getUser(id));
        model.addAttribute("offers", offerService.getOffers());
        return "user_offers";
    }

    @PostMapping("/user_offers/{userId}/{offerId}")
    public String processUserOffersForm(@PathVariable("userId") long userId, @PathVariable("offerId") long offerId, Model model) {
        User user = userService.getUser(userId);
        if (user.getOffer() != null) {
            model.addAttribute("message", "Posiadasz już ofertę");
            return "error";
        }
        Offer offer = offerService.getOffer(offerId);
        user.setOffer(offer);
        user.setPayDate(ZonedDateTime.now().plusMonths(1));
        userService.saveUser(user);
        notificationService.addNotification(NotificationCode.USER_CHANGED_PERSONAL_DATA, user);
        model.addAttribute("user", user);
        model.addAttribute("offers", offerService.getOffers());
        return "user_offers";
    }

    @PostMapping("/user_offers_delete/{userId}")
    public String processUserOffersDeleteForm(@PathVariable("userId") long userId, Model model) {
        User user = userService.getUser(userId);
        if (user.getOffer() == null) {
            model.addAttribute("message", "Nie posiadasz oferty");
            return "error";
        }
        user.setPayDate(null);
        user.setOffer(null);
        userService.saveUser(user);
        notificationService.addNotification(NotificationCode.USER_RESIGN_FROM_OFFER, user);
        model.addAttribute("user", user);
        model.addAttribute("offers", offerService.getOffers());
        return "user_offers";
    }

    @GetMapping("/user_invoice/{id}")
    public String getUserInvoice(@PathVariable("id") long id, Model model) {
        model.addAttribute("user", userService.getUser(id));
        model.addAttribute("invoices", invoiceService.getAllUserInvoices(userService.getUser(id)));
        return "user_invoice";
    }

    @PostMapping("/pay/{userId}")
    public String processPayForm(@PathVariable("userId") long userId, Model model) {
        User user = userService.getUser(userId);
        Offer offer = user.getOffer();
        BigDecimal money = user.getMoney();
        if (money.compareTo(offer.getPrice()) < 0) {
            model.addAttribute("message", "Nie masz wystarczająco środków");
            return "error";
        }
        user.setPayDate(user.getPayDate().plusMonths(1));
        user.setMoney(user.getMoney().subtract(offer.getPrice()));
        invoiceService.createInvoice(user, offer);
        userService.saveUser(user);
        model.addAttribute("user", user);
        return getUserMain(userId, model);
    }

}
