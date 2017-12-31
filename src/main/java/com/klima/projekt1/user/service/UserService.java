package com.klima.projekt1.user.service;

import com.klima.projekt1.user.model.entity.User;
import com.klima.projekt1.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.klima.projekt1.configuration.ErrorCode.USER_NOT_FOUND;
import static com.klima.projekt1.exception.DomainException.of;
@Service
public class UserService {
   private UserRepository userRepository;

   @Autowired
   public UserService(UserRepository userRepository) {
      this.userRepository = userRepository;
   }

   public User getUser(long pesel){
      return userRepository.findOneByPesel(pesel).orElseThrow(() -> of(USER_NOT_FOUND));
   }
}
