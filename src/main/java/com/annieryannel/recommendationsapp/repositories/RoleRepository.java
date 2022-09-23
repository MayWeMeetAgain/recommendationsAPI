package com.annieryannel.recommendationsapp.repositories;

import com.annieryannel.recommendationsapp.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Short> {

    Role findByRole(String role);

}
