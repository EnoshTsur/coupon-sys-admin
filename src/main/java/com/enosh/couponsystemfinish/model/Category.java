package com.enosh.couponsystemfinish.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.Set;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "category")
public class Category extends BaseEntity {

    @Column(name = "type", unique = true, nullable = false)
    @Enumerated(EnumType.STRING)
    private CategoryType type;

    public Category(CategoryType type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Category{" +
                "type=" + type +
                '}';
    }
}
