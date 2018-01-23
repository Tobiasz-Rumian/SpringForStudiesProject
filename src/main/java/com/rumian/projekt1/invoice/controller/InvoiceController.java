package com.rumian.projekt1.invoice.controller;

import com.rumian.projekt1.invoice.service.InvoiceService;
import com.rumian.projekt1.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class InvoiceController {
    private UserService userService;
    private InvoiceService invoiceService;

    @Autowired
    public InvoiceController(UserService userService, InvoiceService invoiceService) {
        this.userService = userService;
        this.invoiceService = invoiceService;
    }

    @GetMapping("/user_invoice/{id}")
    public String getUserInvoice(@PathVariable("id") long id, Model model) {
        model.addAttribute("user", userService.getUser(id));
        model.addAttribute("invoices", invoiceService.getAllUserInvoices(userService.getUser(id)));
        return "user_invoice";
    }
}
