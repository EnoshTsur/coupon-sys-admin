package com.enosh.couponsystemfinish.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.codec.digest.DigestUtils;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotEmpty;

import static org.apache.commons.codec.digest.DigestUtils.*;


@NoArgsConstructor
@MappedSuperclass
public class UserEntity extends BaseEntity {

    @Getter
    @Setter
    @NotEmpty
    @Length(min = 5, max = 30)
    @Column(name = "email", unique = true, nullable = false)
    protected String email;

    @Getter
    @NotEmpty
    @Length(min = 5)
    @Column(name = "password", nullable = false)
    protected String password;

    public void setPassword(String password) {
        this.password = md5Hex(password);
    }
}
