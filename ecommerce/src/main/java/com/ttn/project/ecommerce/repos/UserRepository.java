package com.ttn.project.ecommerce.repos;

import com.ttn.project.ecommerce.entities.registration.User;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface UserRepository extends PagingAndSortingRepository<User,Long> {

    Optional<User> findByEmail(String email);

}
