package com.klima.projekt1.user.service;

import com.klima.projekt1.user.model.entity.User;
import com.klima.projekt1.user.model.entity.principal.UserPrincipalExt;
import com.klima.projekt1.user.repository.UserRepository;
import com.klima.projekt1.user.role.Role;
import io.vavr.control.Try;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import static com.klima.projekt1.configuration.ErrorCode.USER_NOT_FOUND;
import static com.klima.projekt1.exception.DomainException.of;

@Service
public class UserService implements UserDetailsService {
    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    private User getUserByPesel(long pesel) {
        return userRepository.findOneByPesel(pesel).orElseThrow(() -> of(USER_NOT_FOUND));
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> of(USER_NOT_FOUND));
    }

    public boolean checkIfUserExists(String email) {
        return Try.of(() -> userRepository.findByEmail(email)).isEmpty();
    }

    public User getUser(Long id) {
        return getUserByPesel(id);
    }

    public User getUser() {
        return User.builder().role(Role.UNLOGGED).firstName("Anon").build();
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException(username));
        return new UserPrincipalExt(user);
    }

    public void saveUser(User user) {
        userRepository.save(user);
    }
}
