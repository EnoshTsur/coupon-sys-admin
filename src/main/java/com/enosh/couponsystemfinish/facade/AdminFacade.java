package com.enosh.couponsystemfinish.facade;

import com.enosh.couponsystemfinish.dao.CompanyDao;
import com.enosh.couponsystemfinish.dao.CouponDao;
import com.enosh.couponsystemfinish.dao.CustomerDao;
import com.enosh.couponsystemfinish.exceptions.DoesntExistsException;
import com.enosh.couponsystemfinish.exceptions.LoggedOutException;
import com.enosh.couponsystemfinish.model.Company;
import com.enosh.couponsystemfinish.model.Customer;
import com.enosh.couponsystemfinish.validation.CompanyValidation;
import com.enosh.couponsystemfinish.validation.CustomerValidation;
import lombok.Getter;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static org.apache.commons.codec.digest.DigestUtils.*;

@Component
public class AdminFacade extends ClientFacade {

    private final String adminEmail;
    private final String adminPassword;

    private final CompanyValidation companyValidation;
    private final CustomerValidation customerValidation;

    @Getter
    private boolean isLoggedIn = false;

    protected AdminFacade(Environment environment, CompanyDao companyDao, CustomerDao customerDao, CouponDao couponDao, CompanyValidation companyValidation, CustomerValidation customerValidation) {
        super(companyDao, customerDao, couponDao);
        adminEmail = environment.getProperty("admin-email");
        adminPassword = environment.getProperty("admin-password");
        this.companyValidation = companyValidation;
        this.customerValidation = customerValidation;
    }

    @Override
    protected boolean login(String email, String password) {
        return isLoggedIn = (email.equals(adminEmail) && password.equals(adminPassword));
    }

    public Company addCompany(Company company) throws LoggedOutException, IllegalStateException {
        if (!isLoggedIn) throw new LoggedOutException(LOGGED_OUT_MESSAGE);
        return companyDao.addCompany(
                companyValidation.validateAttributes
                        .andThen(companyValidation.validateExistence)
                        .apply(company)
        );
    }

    public Company updateCompany(String email, String password, Long companyId) throws LoggedOutException, DoesntExistsException {
        if (!isLoggedIn) throw new LoggedOutException(LOGGED_OUT_MESSAGE);
        return companyDao.updateCompany(company -> {
            company.setEmail(email);
            company.setPassword(password);
            return companyValidation.validateAttributes
                    .andThen(companyValidation.validateEmailExistence)
                    .apply(company);
        }, companyId);
    }

    public Company deleteCompany(Long companyId) throws LoggedOutException, DoesntExistsException {
        if (!isLoggedIn) throw new LoggedOutException(LOGGED_OUT_MESSAGE);
        return companyDao.findById(companyId).map(company -> {
            company.getCoupons().forEach(coupon ->
                    customerDao.findByCouponsId(coupon.getId())
                            .forEach(customer -> {
                                try {
                                    couponDao.deleteCouponPurchase(
                                            customer.getId(),
                                            coupon.getId()
                                    );
                                } catch (DoesntExistsException e) {
                                    e.printStackTrace();
                                }
                            }));
            try {
                companyDao.deleteCompany(companyId);
            } catch (DoesntExistsException e) {
                e.printStackTrace();
            }
            return company;
        }).orElseThrow(() -> new DoesntExistsException(
                "Company by the id " + companyId +
                        " does not exists in order to delete"
        ));
    }

    public Iterable<Company> getAllCompanies() throws LoggedOutException {
        if (!isLoggedIn) throw new LoggedOutException(LOGGED_OUT_MESSAGE);
        return companyDao.findAll();
    }

    public Optional<Company> getOneCompany(Long id) throws LoggedOutException {
        if (!isLoggedIn) throw new LoggedOutException(LOGGED_OUT_MESSAGE);
        return companyDao.findById(id);
    }

    public Customer addCustomer(Customer customer) throws LoggedOutException, IllegalStateException {
        if (!isLoggedIn) throw new LoggedOutException(LOGGED_OUT_MESSAGE);
        return customerDao.addCustomer(
                customerValidation
                        .validateAttributes.
                        andThen(customerValidation.validateExistence)
                        .apply(customer)
        );
    }

    public Customer updateCustomer(Customer customer) throws LoggedOutException, IllegalStateException, DoesntExistsException {
        if (!isLoggedIn) throw new LoggedOutException(LOGGED_OUT_MESSAGE);
        return customerDao.updateCustomer(c -> {
            c.setFirstName(customer.getFirstName());
            c.setLastName(customer.getLastName());
            c.setEmail(customer.getEmail());
            c.setPassword(customer.getPassword());
            return customerValidation
                    .validateAttributes
                    .andThen(customerValidation.validateExistence)
                    .apply(c);
        }, customer.getId());
    }

    public Customer deleteCustomer(Long id) throws LoggedOutException, DoesntExistsException {
        if (!isLoggedIn) throw new LoggedOutException(LOGGED_OUT_MESSAGE);
        return customerDao.findById(id).map(customer -> {
            customer.getCoupons().forEach(coupon -> {
                try {
                    couponDao.deleteCouponPurchase(id, coupon.getId());
                } catch (DoesntExistsException e) {
                    e.printStackTrace();
                }
            });
            try {
                customerDao.deleteCustomer(id);
            } catch (DoesntExistsException e) {
                e.printStackTrace();
            }
            return customer;
        }).orElseThrow(() -> new DoesntExistsException(
                "Customer by the id " + id +
                        "does not exists in order to delete"
        ));
    }

    public Iterable<Customer> getAllCustomers() throws LoggedOutException {
        if (!isLoggedIn) throw new LoggedOutException(LOGGED_OUT_MESSAGE);
        return customerDao.findAll();
    }

    public Optional<Customer> getOneCustomer(Long id) throws LoggedOutException {
        if (!isLoggedIn) throw new LoggedOutException(LOGGED_OUT_MESSAGE);
        return customerDao.findById(id);
    }

}
