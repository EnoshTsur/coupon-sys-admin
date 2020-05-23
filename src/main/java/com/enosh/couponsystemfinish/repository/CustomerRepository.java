package com.enosh.couponsystemfinish.repository;

import com.enosh.couponsystemfinish.model.Company;
import com.enosh.couponsystemfinish.model.Customer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerRepository extends CrudRepository<Customer, Long> {

    List<Customer> findByCouponsId(Long couponId);

    boolean existsByEmail(String email);
}
