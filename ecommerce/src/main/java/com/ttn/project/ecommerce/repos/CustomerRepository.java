package com.ttn.project.ecommerce.repos;

import com.ttn.project.ecommerce.entities.registration.Customer;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CustomerRepository extends CrudRepository<Customer,Long> {

    List<Customer> findAll(Pageable pageable);
}
