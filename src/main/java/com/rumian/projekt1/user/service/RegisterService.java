package com.rumian.projekt1.user.service;

import com.rumian.projekt1.common.service.CommonService;
import com.rumian.projekt1.user.enums.Role;
import com.rumian.projekt1.user.model.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Service
public class RegisterService {
    private UserService userService;
    private CommonService commonService;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public RegisterService(UserService userService,
                           CommonService commonService,
                           BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userService = userService;
        this.commonService = commonService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    /**
     * Used keys:
     * <p>
     * alreadyRegistered:   Added to map when user already exist in database
     * errors:              Added to map when bindingResult has errors
     * success:             Added to map when registration successful
     * fail:                Added to map when registration failed
     */
    public Map<String, String> registerUser(User user, BindingResult bindingResult, String password) {
        Map<String, String> map = new HashMap<>();
        user.setRole(Role.USER);
        user.setMoney(new BigDecimal(0));
        boolean userExists = userService.checkIfUserExists(user.getEmail());
        if (userExists) {
            addAlreadyRegisteredToMap(map);
            return map;
        }
        if (bindingResult.getErrorCount() > 0) {
            addErrorsToMap(map, commonService.generateErrorMessagefromBindingResult(bindingResult));
            return map;
        }
        addSuccessToMap(map);
        user.setPassword(bCryptPasswordEncoder.encode(password));
        user.setEnabled(true);
        userService.saveUser(user);
        return map;
    }

    private void addErrorsToMap(Map<String, String> map, String errors) {
        map.put("errors", errors);
        map.put("fail", "");
    }

    private void addAlreadyRegisteredToMap(Map<String, String> map) {
        map.put("alreadyRegistered", "");
        map.put("fail", "");
    }

    private void addSuccessToMap(Map<String, String> map) {
        map.put("success", "");
    }
}
