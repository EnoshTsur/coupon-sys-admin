package com.enosh.couponsystemfinish.dao;

import com.enosh.couponsystemfinish.exceptions.DoesntExistsException;
import com.enosh.couponsystemfinish.model.CategoryType;
import com.enosh.couponsystemfinish.model.Coupon;
import com.enosh.couponsystemfinish.model.Customer;
import com.enosh.couponsystemfinish.repository.CompanyRepository;
import com.enosh.couponsystemfinish.repository.CouponRepository;
import com.enosh.couponsystemfinish.repository.CustomerRepository;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Primary
@Component
public class CouponDbdao implements CouponDao {

    private final CouponRepository repository;

    private final CompanyDao companyDao;
    private final CompanyRepository companyRepository;
    private final CustomerRepository customerRepository;
    private final CategoryDao categoryDao;

    public CouponDbdao(CouponRepository repository, CompanyDao companyDao, CompanyRepository companyRepository, CustomerRepository customerRepository, CategoryDao categoryDao) {
        this.repository = repository;
        this.companyDao = companyDao;
        this.companyRepository = companyRepository;
        this.customerRepository = customerRepository;
        this.categoryDao = categoryDao;
    }

    @Override
    public Coupon addCoupon(Coupon coupon, Long companyId, CategoryType type) throws DoesntExistsException {
        return companyDao.findById(companyId)
                .map(company -> {
                    coupon.setCompany(company);
                    coupon.setCategory(categoryDao.getOrCreate(type));
                    return repository.save(coupon);
                }).orElseThrow(() -> new DoesntExistsException(
                                "Company by the id " + companyId +
                                        " does not exists in order to create coupon"
                        )
                );
    }

    @Override
    public Coupon updateCoupon(Function<Coupon, Coupon> mapper, Long id) throws DoesntExistsException {
        return findById(id)
                .map(byId -> mapper.andThen(repository::save).apply(byId))
                .orElseThrow(() -> new DoesntExistsException(
                                "Coupon by the id " + id +
                                        " does not exists in order to update"
                        )
                );
    }

    @Override
    public Coupon deleteCoupon(Long id, Long companyId) throws DoesntExistsException {
        return findById(id).map(coupon -> {
            repository.delete(coupon);
            return coupon;
        }).orElseThrow(() -> new DoesntExistsException(
                        "Coupon by the id " + id +
                                "doesnt exists in order to delete"
                )
        );
    }

    @Override
    public List<Coupon> findAll() {
        List<Coupon> coupons = new ArrayList<>();
        repository.findAll().iterator().forEachRemaining(coupons::add);
        return coupons;
    }

    @Override
    public Optional<Coupon> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public Coupon addCouponPurchase(Long customerId, Long couponId) throws DoesntExistsException {
        return findById(couponId).map(coupon ->
                customerRepository.findById(customerId).map(customer ->  {
                    List<Coupon> customerCoupons = customer.getCoupons();
                    if (!customerCoupons.contains(coupon)) {
                        customerCoupons.add(coupon);
                        customer.setCoupons(customerCoupons);
                        customerRepository.save(customer);
                        return coupon;
                    }
                    return coupon;
                }).orElse(null))
                .orElseThrow(() -> new DoesntExistsException(
                        "Coupon or Customer by: " +
                                new HashMap<String, Long>(){{
                                    put("Customer ID", customerId);
                                    put("Coupon ID", couponId);
                                }}
                                 +
                                "doesnt exists in order to purchase"
                ));

    }

    @Override
    public Coupon deleteCouponPurchase(Long customerId, Long couponId) throws DoesntExistsException {
        return customerRepository.findById(customerId).map(customer -> {
                    customer.setCoupons(
                            customer.getCoupons()
                            .stream()
                            .filter(c -> !c.getId().equals(couponId))
                            .collect(Collectors.toList())
                    );
                    customerRepository.save(customer);
                    return findById(couponId).orElse(null);
                }).orElseThrow(() -> new DoesntExistsException(
                "Coupon or Customer by: " +
                        new HashMap<String, Long>(){{
                            put("Customer ID", customerId);
                            put("Coupon ID", couponId);
                        }}
                        +
                        "doesnt exists in order to remove purchase"
        ));
    }
}
