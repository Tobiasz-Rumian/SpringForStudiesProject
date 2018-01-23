package com.rumian.projekt1.common.service;

import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import java.util.stream.Collectors;

@Service
public class CommonService {

    //TODO: Generate more user readable error
    public String generateErrorMessagefromBindingResult(BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return bindingResult.getAllErrors()
                    .stream()
                    .map(ObjectError::toString)
                    .collect(Collectors.joining(" \n"));
        }
        return null;
    }


}
