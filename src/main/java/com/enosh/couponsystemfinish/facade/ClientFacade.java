package com.enosh.couponsystemfinish.facade;

import com.enosh.couponsystemfinish.dao.CompanyDao;
import com.enosh.couponsystemfinish.dao.CouponDao;
import com.enosh.couponsystemfinish.dao.CustomerDao;
import org.springframework.stereotype.Service;

@Service
public abstract class ClientFacade {

    protected final CompanyDao companyDao;
    protected final CustomerDao customerDao;
    protected final CouponDao couponDao;

    protected final String LOGGED_OUT_MESSAGE = "In order to continue, you must log in";

    protected ClientFacade(CompanyDao companyDao, CustomerDao customerDao, CouponDao couponDao) {
        this.companyDao = companyDao;
        this.customerDao = customerDao;
        this.couponDao = couponDao;
    }

    protected abstract boolean login(String email, String password);


}
