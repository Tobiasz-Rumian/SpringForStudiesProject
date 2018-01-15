package com.klima.projekt1.configuration;

import com.klima.projekt1.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

@Component
@Slf4j
public class AuthenticationSuccessHandlerExt implements AuthenticationSuccessHandler {
    private UserService userService;
    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Autowired
    public AuthenticationSuccessHandlerExt(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest arg0, HttpServletResponse arg1, Authentication authentication)
            throws IOException, ServletException {
        long pesel = userService.getUserByEmail(authentication.getName()).getPesel();
        try {
            switch (new ArrayList<>(authentication.getAuthorities()).get(0).getAuthority()) {
                case "USER":
                    redirectStrategy.sendRedirect(arg0, arg1, "/home/" + pesel);//TODO: delete unnecesery database call
                    break;
                case "ADMIN":
                    redirectStrategy.sendRedirect(arg0, arg1, "/admin_console/" + pesel);
                    break;
                default:
                    throw new IllegalStateException();
            }
        } catch (Exception e) {
            log.error("Error regarding user Rule: ", e);
        }
    }

}
