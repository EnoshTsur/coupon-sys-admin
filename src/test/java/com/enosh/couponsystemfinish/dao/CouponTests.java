package com.enosh.couponsystemfinish.dao;

import com.enosh.couponsystemfinish.dao.CouponDao;
import com.enosh.couponsystemfinish.exceptions.DoesntExistsException;
import com.enosh.couponsystemfinish.model.CategoryType;
import com.enosh.couponsystemfinish.model.Coupon;
import com.enosh.couponsystemfinish.model.Customer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.stream.Stream;

@SpringBootTest
class CouponTests {

    @Autowired
    private CouponDao couponDao;

    @Test
    void contextLoads() {
    }

    @Test
    void create() {


        Stream.of(
                new Coupon(
                        "TV",
                        LocalDate.now(),
                        LocalDate.now().plusYears(1),
                        100,
                        79.90,
                        "http://blablabla..."),
                new Coupon(
                        "Cables",
                        LocalDate.now(),
                        LocalDate.now().plusYears(1),
                        100,
                        79.90,
                        "http://blablabla...")
        ).map(coupon -> {
            try {
                return couponDao.addCoupon(coupon, 21L, CategoryType.ELECTRICITY);
            } catch (DoesntExistsException e) {
                return null;
            }
        }).forEach(System.out::println);

    }

    @Test
    void update() {
        try {
            Coupon updated = couponDao.updateCoupon(coupon -> {
                coupon.setEndDate(LocalDate.now().plusYears(2));
                coupon.setAmount(99);
                coupon.setPrice(12.90);
                return coupon;
            }, 10L);

            System.out.println(updated);
        } catch (DoesntExistsException e) {
            e.printStackTrace();
        }
    }

    @Test
    void findById() {
        couponDao.findById(10L).ifPresent(System.out::println);
    }

    @Test
    void findAll() {
        couponDao.findAll().forEach(System.out::println);
    }

    @Test
    void delete() {
        try {
            Coupon deleted = couponDao.deleteCoupon(10L, 17L);
            System.out.println(deleted);
        } catch (DoesntExistsException e) {
            e.printStackTrace();
        }
    }

    @Test
    void purchase(){
        try {

            Coupon purchased = couponDao.addCouponPurchase(8L, 17L);
            System.out.println(purchased);
        } catch (DoesntExistsException e) {
            e.printStackTrace();
        }
    }

    @Test
    void removePurchase(){
        try {
            Coupon removedPurchase =
                    couponDao.deleteCouponPurchase(8L, 15L);
            System.out.println(removedPurchase);
        } catch (DoesntExistsException e) {
            e.printStackTrace();
        }
    }

}
