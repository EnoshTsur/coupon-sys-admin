package com.enosh.couponsystemfinish.dao;

import com.enosh.couponsystemfinish.model.Category;
import com.enosh.couponsystemfinish.model.CategoryType;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.function.Function;

@Service
public interface CategoryDao {

    Optional<Category> findByType(CategoryType type);

    Category getOrCreate(CategoryType type);

}
