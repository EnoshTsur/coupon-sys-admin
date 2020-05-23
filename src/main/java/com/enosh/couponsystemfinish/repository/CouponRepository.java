package com.enosh.couponsystemfinish.repository;

import com.enosh.couponsystemfinish.model.Company;
import com.enosh.couponsystemfinish.model.Coupon;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CouponRepository extends CrudRepository<Coupon, Long> {
}
