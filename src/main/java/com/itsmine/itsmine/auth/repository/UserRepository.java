package com.itsmine.itsmine.auth.repository;

import com.itsmine.itsmine.auth.domain.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);


}
