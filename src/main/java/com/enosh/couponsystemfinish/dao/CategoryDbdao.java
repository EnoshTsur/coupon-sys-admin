package com.enosh.couponsystemfinish.dao;

import com.enosh.couponsystemfinish.model.Category;
import com.enosh.couponsystemfinish.model.CategoryType;
import com.enosh.couponsystemfinish.repository.CategoryRepository;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.function.Function;

@Primary
@Component
public class CategoryDbdao implements CategoryDao {

    private final CategoryRepository repository;

    public CategoryDbdao(CategoryRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<Category> findByType(CategoryType type) {
        return repository.findByType(type);
    }

    @Override
    public Category getOrCreate(CategoryType type) {
        return findByType(type).orElseGet(() -> repository.save(new Category(type)));
    }
}
