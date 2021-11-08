package com.ttn.project.ecommerce.repos;

import com.ttn.project.ecommerce.entities.registration.Seller;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface SellerRepository extends PagingAndSortingRepository<Seller,Long> {

    Optional<Seller> findByCompanyName(String companyName);
    Seller findByGst(String gst);
}
