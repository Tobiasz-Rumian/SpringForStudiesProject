package com.klima.projekt1.offer.controller;

import com.klima.projekt1.notification.service.NotificationService;
import com.klima.projekt1.offer.mapper.OfferMapper;
import com.klima.projekt1.offer.model.entity.Offer;
import com.klima.projekt1.offer.service.OfferService;
import com.klima.projekt1.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Map;

@Controller
public class OfferController {
    private UserService userService;
    private OfferService offerService;
    private OfferMapper offerMapper;
    private NotificationService notificationService;

    @Autowired
    public OfferController(UserService userService, OfferService offerService, OfferMapper offerMapper, NotificationService notificationService) {
        this.userService = userService;
        this.offerService = offerService;
        this.offerMapper = offerMapper;
        this.notificationService = notificationService;
    }

    @GetMapping("/admin_offers/{id}")
    public String getAdminOffers(@PathVariable("id") long id, Model model) {
        model.addAttribute("offers", offerMapper.toOfferDTOs(offerService.getOffers()));
        model.addAttribute("user", userService.getUser(id));
        model.addAttribute("unreadNotificationNumber", notificationService.getNumberOfUnreadNotifications());

        return "admin_offers";
    }

    @PostMapping("/admin_addOffer/{userId}")
    public String processAdminAddOffer(@PathVariable("userId") long userId, Model model,
                                       @Valid Offer offer, BindingResult bindingResult,
                                       HttpServletRequest request, @RequestParam Map requestParams,
                                       RedirectAttributes redir) {
        if (bindingResult.hasErrors()) return "error";//TODO: Add error page
        if (!requestParams.get("offerId").equals("null")) {
            Offer isCreated = offerService.getOffer(Long.parseLong((String) requestParams.get("offerId")));
            if (isCreated != null) {
                offer.setId(Long.parseLong((String) requestParams.get("offerId")));
            }
        }
        offerService.saveOffer(offer);
        return getAdminOffers(userId, model);
    }

    @GetMapping("/admin_addOffer/{id}")
    public String getAdminAddOffer(@PathVariable("id") long id, Model model) {
        model.addAttribute("user", userService.getUser(id));
        model.addAttribute("offer", new Offer());
        model.addAttribute("unreadNotificationNumber", notificationService.getNumberOfUnreadNotifications());

        return "admin_addOffer";
    }
    @GetMapping("/admin_editOffer/{id}/{offerId}")
    public String getAdminEditOffer(@PathVariable("id") long id,@PathVariable("offerId") long offerId, Model model) {
        model.addAttribute("user", userService.getUser(id));
        model.addAttribute("offer", offerService.getOffer(offerId));
        model.addAttribute("unreadNotificationNumber", notificationService.getNumberOfUnreadNotifications());

        return "admin_addOffer";
    }


    @PostMapping("/admin_deleteOffer/{id}/{offerId}")
    public String deleteOffer(@PathVariable("id") long id,@PathVariable("offerId") long offerId, Model model) {
        offerService.deleteOffer(offerId);
        return getAdminOffers(id, model);
    }
}
