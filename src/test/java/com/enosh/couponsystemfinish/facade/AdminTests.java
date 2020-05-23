package com.enosh.couponsystemfinish.facade;

import com.enosh.couponsystemfinish.exceptions.DoesntExistsException;
import com.enosh.couponsystemfinish.exceptions.LoggedOutException;
import com.enosh.couponsystemfinish.model.Company;
import com.enosh.couponsystemfinish.model.Customer;
import com.enosh.couponsystemfinish.repository.CustomerRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.stream.Stream;

@SpringBootTest
public class AdminTests {

    @Autowired
    private AdminFacade adminFacade;

    @Autowired
    private CustomerRepository repository;

    @Test
    void contextLoads() {
    }

    @BeforeEach
    void init() {
        adminFacade.login("admin@admin.com", "admin");

    }

    @Test
    void login() {

        boolean isIt = adminFacade.login("admin@admin.com", "admin");
        System.out.println(isIt);
        System.out.println(adminFacade.isLoggedIn());
    }

    @Test
    void addCompany() {
        try {
            Company added = adminFacade.addCompany(new Company(
                    "Leumi",
                    "leu@gm",
                    "123123"
            ));
            System.out.println(added);
        } catch (LoggedOutException e) {
            e.printStackTrace();
        } catch (IllegalStateException e) {
            Stream.of(e.getSuppressed())
                    .map(Throwable::getMessage)
                    .forEach(System.out::println);
        }
    }

    @Test
    void updateCompany() {

        try {
            Company updated = adminFacade.updateCompany(
                    "gogo@gm", "12345", 21L
            );
            System.out.println(updated);
        } catch (LoggedOutException e) {
            e.printStackTrace();
        } catch (DoesntExistsException e) {
            e.printStackTrace();
        } catch (IllegalStateException e) {
            Stream.of(e.getSuppressed())
                    .map(Throwable::getMessage)
                    .forEach(System.out::println);
        }
    }

    @Test
    void deleteCompany() {
        try {
            Company deleted = adminFacade.deleteCompany(21L);
            System.out.println(deleted);
        } catch (LoggedOutException | DoesntExistsException e) {
            e.printStackTrace();
        }
    }

    @Test
    void findAlCompanies() {
        try {
            adminFacade.getAllCompanies().forEach(System.out::println);
        } catch (LoggedOutException e) {
            e.printStackTrace();
        }
    }

    @Test
    void findOneCompany() {
        try {
            adminFacade.getOneCompany(22L)
                    .ifPresent(System.out::println);
        } catch (LoggedOutException e) {
            e.printStackTrace();
        }
    }

    @Test
    void addCustomer() {
        try {
            System.out.println(
                    adminFacade.addCustomer(new Customer(
                            "Sonya",
                            "Wins",
                            "mk3@sonya.com",
                            "123478"
                    )));
        } catch (LoggedOutException e) {
            e.printStackTrace();
        } catch (IllegalStateException e) {
            Stream.of(e.getSuppressed())
                    .map(Throwable::getMessage)
                    .forEach(System.out::println);
        }
    }

    @Test
    void updateCustomer(){
        try {
            Customer updated = adminFacade.updateCustomer(
                    new Customer(
                            10L,
                            "Koby",
                            "Yanai",
                            "koby@gmail.com",
                            "12334"
                    )
            );
            System.out.println(updated);
        } catch (LoggedOutException e) {
            e.printStackTrace();
        } catch (DoesntExistsException e) {
            e.printStackTrace();
        } catch (IllegalStateException e) {
            Stream.of(e.getSuppressed())
                    .map(Throwable::getMessage)
                    .forEach(System.out::println);
        }
    }

    @Test
    void deleteCustomer(){
        try {
            Customer deleted = adminFacade.deleteCustomer(10L);
            System.out.println(deleted);
        } catch (LoggedOutException | DoesntExistsException e) {
            e.printStackTrace();
        }
    }

    @Test
    void getAllCustomers(){
        try {
            adminFacade.getAllCustomers().forEach(System.out::println);
        } catch (LoggedOutException e) {
            e.printStackTrace();
        }
    }

    @Test
    void getOneCustomer(){
        try {
            adminFacade.getOneCustomer(11L).ifPresent(System.out::println);
        } catch (LoggedOutException e) {
            e.printStackTrace();
        }
    }

}
