package com.klima.projekt1.user.repository;

import com.klima.projekt1.user.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.ZonedDateTime;
import java.util.Optional;

@Repository("userRepository")
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findOneByPesel(Long pesel);
    Optional<User> findOneById(Long id);
    Optional<User> findByEmail(String email);

    long countAllByPayDateBefore(ZonedDateTime date);
}
