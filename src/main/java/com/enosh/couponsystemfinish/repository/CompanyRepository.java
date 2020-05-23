package com.enosh.couponsystemfinish.repository;

import com.enosh.couponsystemfinish.model.Company;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyRepository extends CrudRepository<Company, Long> {

    boolean existsByEmailAndPassword(String email, String password);

    boolean existsByName(String name);

    boolean existsByEmail(String email);
}
