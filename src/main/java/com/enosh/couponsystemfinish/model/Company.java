package com.enosh.couponsystemfinish.model;

import lombok.*;
import org.apache.tomcat.jni.User;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "company")
public class Company extends UserEntity {

    @NotEmpty
    @Length(min = 2, max = 30)
    @Column(name = "name", unique = true, nullable = false)
    private String name;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "company",
            orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Coupon> coupons = new ArrayList<>();

    public Company(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    @Override
    public String toString() {
        return "Company{" +
                "name='" + name + '\'' +
                ", coupons=" + coupons +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", id=" + id +
                '}';
    }
}
