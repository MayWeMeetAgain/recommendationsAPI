package com.annieryannel.recommendationsapp.repositories;

import com.annieryannel.recommendationsapp.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);

    boolean existsByUsername(String username);

}
