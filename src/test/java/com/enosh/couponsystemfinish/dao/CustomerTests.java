package com.enosh.couponsystemfinish.dao;

import com.enosh.couponsystemfinish.dao.CustomerDao;
import com.enosh.couponsystemfinish.exceptions.DoesntExistsException;
import com.enosh.couponsystemfinish.model.Customer;
import com.enosh.couponsystemfinish.repository.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.stream.Stream;

@SpringBootTest
class CustomerTests {

    @Autowired
    private CustomerDao customerDao;

    @Test
    void contextLoads() {
    }

    @Test
    void create() {
        Stream.of(
                new Customer(
                        "Avner",
                        "Shulman",
                        "as@gmail.com",
                        "123456"
                ),
                new Customer(
                        "Yoram",
                        "Nissim",
                        "ys@gmail.com",
                        "123456"
                ),
                new Customer(
                        "Efrat",
                        "Gabay",
                        "eg@gmail.com",
                        "123456"
                ),
                new Customer(
                        "Ori",
                        "Itshaki",
                        "oi@gmail.com",
                        "123456"
                )
        )
                .map(customerDao::addCustomer)
                .forEach(System.out::println);
    }

    @Test
    void update() {

        try {
            Customer updated = customerDao.updateCustomer(c ->{
                c.setFirstName("Kobi");
                c.setLastName("Popo");
                return c;
            }, 6L);
            System.out.println(updated);
        } catch (DoesntExistsException e) {
            e.printStackTrace();
        }
    }

    @Test
    void delete() {

        try {
            Customer deleted = customerDao.deleteCustomer(6L);
            System.out.println(deleted);
        } catch (DoesntExistsException e) {
            e.printStackTrace();
        }
    }

    @Test
    void findById(){
        customerDao.findById(9L).ifPresent(System.out::println);
    }

    @Test
    void findAll(){
        customerDao.findAll().forEach(System.out::println);
    }

    @Test
    void existsByEmail(){
        System.out.println(customerDao.existsByEmail("oi@gmail.com"));
    }

}
