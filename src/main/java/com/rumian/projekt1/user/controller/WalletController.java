package com.rumian.projekt1.user.controller;

import com.rumian.projekt1.exception.controller.ErrorController;
import com.rumian.projekt1.invoice.service.InvoiceService;
import com.rumian.projekt1.offer.model.entity.Offer;
import com.rumian.projekt1.user.model.dto.MoneyTransferDto;
import com.rumian.projekt1.user.model.entity.User;
import com.rumian.projekt1.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;

import javax.validation.Valid;
import java.math.BigDecimal;

@Controller
public class WalletController {
    private UserService userService;
    private InvoiceService invoiceService;
    private UserController userController;
    private ErrorController errorController;

    @Autowired
    public WalletController(UserService userService,
                            InvoiceService invoiceService,
                            UserController userController,
                            ErrorController errorController) {
        this.userService = userService;
        this.invoiceService = invoiceService;
        this.userController = userController;
        this.errorController = errorController;
    }

    @GetMapping("/user_wallet/{id}")
    public String getUserWallet(@PathVariable("id") long id, Model model) {
        model.addAttribute("user", userService.getUser(id));
        model.addAttribute("amount", new MoneyTransferDto(0));
        return "user_wallet";
    }

    @PostMapping("/user_wallet/{userId}")
    public String processUserWalletForm(@PathVariable("userId") long userId,
                                        @Valid MoneyTransferDto amount,
                                        Model model) {
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

    @PostMapping("/pay/{userId}")
    public RedirectView processPayForm(@PathVariable("userId") long userId, Model model) {
        User user = userService.getUser(userId);
        Offer offer = user.getOffer();
        BigDecimal money = user.getMoney();
        if (money.compareTo(offer.getPrice()) < 0) {
            model.addAttribute("message", "Nie masz wystarczająco środków");
            return new RedirectView(errorController.getErrorForRegisteredUser(userId, model));
        }
        user.setPayDate(user.getPayDate().plusMonths(1));
        user.setMoney(user.getMoney().subtract(offer.getPrice()));
        user.getInvoices().add(invoiceService.createInvoice(user, offer));
        userService.saveUser(user);
        model.addAttribute("user", user);
        return new RedirectView(userController.getUserMain(userId, model));
    }
}
