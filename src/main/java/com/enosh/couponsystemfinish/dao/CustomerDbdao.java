package com.enosh.couponsystemfinish.dao;

import com.enosh.couponsystemfinish.exceptions.DoesntExistsException;
import com.enosh.couponsystemfinish.model.Customer;
import com.enosh.couponsystemfinish.repository.CustomerRepository;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import static org.apache.commons.codec.digest.DigestUtils.*;

@Primary
@Component
public class CustomerDbdao implements CustomerDao {

    private final CustomerRepository repository;

    public CustomerDbdao(CustomerRepository repository) {
        this.repository = repository;
    }

    @Override
    public boolean existsByEmailAndPassword(String email, String password) {
        return false;
    }

    @Override
    public Customer addCustomer(Customer customer) {
        customer.setPassword(md5Hex(customer.getPassword()));
        return repository.save(customer);
    }

    @Override
    public Customer updateCustomer(Function<Customer, Customer> mapper, Long id) throws DoesntExistsException {
        return findById(id)
                .map(customer -> mapper.andThen(repository::save).apply(customer))
                .orElseThrow(() -> new DoesntExistsException(
                        "Customer by the id " + id +
                                " does not exists in order to update"
                ));
    }

    @Override
    public Customer deleteCustomer(Long id) throws DoesntExistsException {
        return findById(id)
                .map(customer -> {
                    repository.delete(customer);
                    return customer;
                }).orElseThrow(() -> new DoesntExistsException(
                        "Customer by the id " + id +
                                " does not exists in order to delete"
                ));
    }

    @Override
    public Iterable<Customer> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<Customer> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public List<Customer> findByCouponsId(Long couponId) {
        return repository.findByCouponsId(couponId);
    }

    @Override
    public boolean existsByEmail(String email) {
        return repository.existsByEmail(email);
    }
}
