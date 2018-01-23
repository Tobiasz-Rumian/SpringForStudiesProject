package com.rumian.projekt1.offer.controller;

import com.rumian.projekt1.common.service.CommonService;
import com.rumian.projekt1.notification.model.enums.NotificationCode;
import com.rumian.projekt1.notification.service.NotificationService;
import com.rumian.projekt1.offer.mapper.OfferMapper;
import com.rumian.projekt1.offer.model.entity.Offer;
import com.rumian.projekt1.offer.service.OfferService;
import com.rumian.projekt1.user.model.entity.User;
import com.rumian.projekt1.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.time.ZonedDateTime;
import java.util.Map;
import java.util.Optional;

@Controller
public class OfferController {
    private static final String NULL = "null";
    private static final String ERROR_MESSAGE_SITE = "error";
    private static final String ERROR_MESSAGE_KEY = "message";
    private static final String USER_ID_KEY = "userId";
    private static final String OFFER_ID_KEY = "offerId";
    private UserService userService;
    private OfferService offerService;
    private OfferMapper offerMapper;
    private NotificationService notificationService;
    private CommonService commonService;
    @Autowired
    public OfferController(UserService userService,
                           OfferService offerService,
                           OfferMapper offerMapper,
                           NotificationService notificationService,
                           CommonService commonService) {
        this.userService = userService;
        this.offerService = offerService;
        this.offerMapper = offerMapper;
        this.notificationService = notificationService;
        this.commonService = commonService;
    }

    @GetMapping("/admin_offers/{id}")
    public String getAdminOffers(@PathVariable("id") long id, Model model) {
        model.addAttribute("offers", offerMapper.toOfferDTOs(offerService.getOffers()));
        model.addAttribute("user", userService.getUser(id));
        model.addAttribute("unreadNotificationNumber", notificationService.getNumberOfUnreadNotifications());
        return "admin_offers";
    }

    @PostMapping("/admin_addOffer/{userId}")
    public String processAdminAddOffer(@PathVariable(USER_ID_KEY) long userId,
                                       Model model,
                                       @Valid Offer offer,
                                       BindingResult bindingResult,
                                       @RequestParam Map requestParams) {
        if (bindingResult.hasErrors()) {
            model.addAttribute(ERROR_MESSAGE_KEY, commonService.generateErrorMessagefromBindingResult(bindingResult));
            return ERROR_MESSAGE_SITE;
        }
        if (!requestParams.get(OFFER_ID_KEY).equals(NULL)) {
            Optional<Offer> existingOffer = Optional.of(
                    offerService.getOffer(Long.parseLong((String) requestParams.get(OFFER_ID_KEY))));
            if (!existingOffer.isPresent()) offer.setId(Long.parseLong((String) requestParams.get(OFFER_ID_KEY)));
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
    public String getAdminEditOffer(@PathVariable("id") long id, @PathVariable(OFFER_ID_KEY) long offerId, Model model) {
        model.addAttribute("user", userService.getUser(id));
        model.addAttribute("offer", offerService.getOffer(offerId));
        model.addAttribute("unreadNotificationNumber", notificationService.getNumberOfUnreadNotifications());
        return "admin_addOffer";
    }


    @PostMapping("/admin_deleteOffer/{id}/{offerId}")
    public String deleteOffer(@PathVariable("id") long id, @PathVariable(OFFER_ID_KEY) long offerId, Model model) {
        offerService.deleteOffer(offerId);
        return getAdminOffers(id, model);
    }

    @GetMapping("/user_offers/{id}")
    public String getUserOffers(@PathVariable("id") long id, Model model) {
        model.addAttribute("user", userService.getUser(id));
        model.addAttribute("offers", offerService.getOffers());
        return "user_offers";
    }

    @PostMapping("/user_offers/{userId}/{offerId}")
    public String processUserOffersForm(@PathVariable("userId") long userId,
                                        @PathVariable("offerId") long offerId,
                                        Model model) {
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
}
