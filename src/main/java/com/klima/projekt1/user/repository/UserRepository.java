package com.klima.projekt1.user.repository;

import com.klima.projekt1.user.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository  extends JpaRepository<User, Long> {
   Optional<User> findOneByPesel(Long pesel);
}
