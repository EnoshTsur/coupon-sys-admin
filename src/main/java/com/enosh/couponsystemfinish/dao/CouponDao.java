package com.enosh.couponsystemfinish.dao;

import com.enosh.couponsystemfinish.exceptions.DoesntExistsException;
import com.enosh.couponsystemfinish.model.CategoryType;
import com.enosh.couponsystemfinish.model.Coupon;
import com.enosh.couponsystemfinish.model.Customer;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Service
public interface CouponDao {

    Coupon addCoupon(Coupon coupon, Long companyId, CategoryType type)throws DoesntExistsException;

    Coupon updateCoupon(Function<Coupon, Coupon> mapper, Long id)throws DoesntExistsException;

    Coupon deleteCoupon(Long id, Long companyId) throws DoesntExistsException;

    List<Coupon> findAll();

    Optional<Coupon> findById(Long id);

    Coupon addCouponPurchase(Long customerId, Long couponId) throws DoesntExistsException;

    Coupon deleteCouponPurchase(Long customerId, Long couponId) throws DoesntExistsException;

}
