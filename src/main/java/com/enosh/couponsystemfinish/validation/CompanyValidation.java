package com.enosh.couponsystemfinish.validation;

import com.enosh.couponsystemfinish.dao.CompanyDao;
import com.enosh.couponsystemfinish.model.Company;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class CompanyValidation extends EntityValidation {

    @Autowired
    private CompanyDao companyDao;


    public Function<Company, Company> validateNameExistence = company ->  Validator.of(company)
                .validate(
                        Company::getName,
                        name -> !companyDao.existsByName(name),
                        existsByError(company.getName(), NAME)
                )
                .get();

    public Function<Company, Company> validateEmailExistence = company ->  Validator.of(company)
            .validate(
                    Company::getEmail,
                    email -> !companyDao.existsByEmail(email),
                    existsByError(company.getEmail(), EMAIL)
            )
            .get();

    public Function<Company, Company> validateExistence = company ->
            validateNameExistence.andThen(validateEmailExistence).apply(company);

    public Function<Company, Company> validateAttributes = company -> Validator.of(company)
                .validate(
                        Company::getName,
                        isValidRange(2, 30),
                        lengthError(NAME, 2)
                )
                .validate(
                        Company::getEmail,
                        isValidRange(5, 30),
                        lengthError(EMAIL, 5)
                )
                .validate(
                        Company::getPassword,
                        isValidRange(5, 255),
                        lengthError(PASSWORD, 5)
                )
                .get();

}
