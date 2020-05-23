package com.enosh.couponsystemfinish.dao;

import com.enosh.couponsystemfinish.exceptions.DoesntExistsException;
import com.enosh.couponsystemfinish.model.Company;
import com.enosh.couponsystemfinish.repository.CompanyRepository;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import static org.apache.commons.codec.digest.DigestUtils.*;

@Component
@Primary
public class CompanyDbdao implements CompanyDao {

    private final CompanyRepository repository;

    public CompanyDbdao(CompanyRepository repository) {
        this.repository = repository;
    }

    @Override
    public boolean existsByEmailAndPassword(String email, String password) {
        return repository.existsByEmailAndPassword(email, md5Hex(password));
    }

    @Override
    public Company addCompany(Company company) throws IllegalStateException {
        company.setPassword(md5Hex(company.getPassword()));
        return repository.save(company);
    }

    @Override
    public Company updateCompany(Function<Company, Company> mapper, Long id) throws DoesntExistsException {
        return findById(id)
                .map(byId -> mapper.andThen(repository::save).apply(byId))
                .orElseThrow(() -> new DoesntExistsException(
                                "Company by the id " + id +
                                        " does not exists in order to update"
                        )
                );

    }


    @Override
    public Company deleteCompany(Long id) throws DoesntExistsException {
        return findById(id).map(company -> {
            repository.delete(company);
            return company;
        }).orElseThrow(() -> new DoesntExistsException(
                        "Company by the id " + id +
                                " does not exists in order to delete"
                )
        );
    }

    @Override
    public List<Company> findAll() {
        List<Company> companies = new ArrayList<>();
        repository.findAll().iterator().forEachRemaining(companies::add);
        return companies;
    }

    @Override
    public Optional<Company> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public boolean existsByName(String name) {
        return repository.existsByName(name);
    }

    @Override
    public boolean existsByEmail(String email) {
        return repository.existsByEmail(email);
    }
}
