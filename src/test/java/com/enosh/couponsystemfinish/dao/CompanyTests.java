package com.enosh.couponsystemfinish.dao;

import com.enosh.couponsystemfinish.dao.CompanyDao;
import com.enosh.couponsystemfinish.exceptions.DoesntExistsException;
import com.enosh.couponsystemfinish.model.Company;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

@SpringBootTest
class CompanyTests {

    @Autowired
    private CompanyDao companyDao;

    @Test
    void contextLoads() {
    }

    @Test
    void add() {
        Stream.of(new Company(
                "rainbow",
                "rainbow@com",
                "l2323"
        ), new Company(
                "yes",
                "yes@gm",
                "223354"
        ), new Company(
                "bug",
                "2njk@jkj",
                "545454"
        ), new Company(
                "sports",
                "sp@hopj",
                "443219"
        )).map(companyDao::addCompany)
                .forEach(System.out::println);

    }

    @Test
    void update() {
        try {
            Company updated = companyDao.updateCompany(c -> {
                c.setName("aromale");
                return c;
            }, 17L);

            System.out.println(updated);
        } catch (DoesntExistsException e) {
            e.printStackTrace();
        }
    }

    @Test
    void findById() {
        companyDao.findById(18L).ifPresent(System.out::println);
    }

    @Test
    void findAll() {
        companyDao.findAll().forEach(System.out::println);
    }

    @Test
    void delete() {
        try {
            Company deleted = companyDao.deleteCompany(17L);
            System.out.println(deleted);
        } catch (DoesntExistsException e) {
            e.printStackTrace();
        }
    }

    @Test
    void existsByName(){
        System.out.println(companyDao.existsByName("aromale"));
    }

    @Test
    void existsByEmail(){
        System.out.println(companyDao.existsByEmail("yes@gm"));
    }
}
