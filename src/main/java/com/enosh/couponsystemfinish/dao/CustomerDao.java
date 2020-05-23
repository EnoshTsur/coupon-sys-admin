package com.enosh.couponsystemfinish.dao;

import com.enosh.couponsystemfinish.exceptions.DoesntExistsException;
import com.enosh.couponsystemfinish.model.Customer;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Service
public interface CustomerDao {

    boolean existsByEmailAndPassword(String email, String password);

    Customer addCustomer(Customer customer);

    Customer updateCustomer(Function<Customer, Customer> mapper, Long id) throws DoesntExistsException;

    Customer deleteCustomer(Long id) throws DoesntExistsException;

    Iterable<Customer> findAll();

    Optional<Customer> findById(Long id);

    List<Customer> findByCouponsId(Long couponId);

    boolean existsByEmail(String email);
}
