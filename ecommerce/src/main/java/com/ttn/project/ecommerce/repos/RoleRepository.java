package com.ttn.project.ecommerce.repos;

import com.ttn.project.ecommerce.entities.registration.Role;
import org.springframework.data.repository.CrudRepository;

public interface RoleRepository extends CrudRepository<Role,Long> {
}
