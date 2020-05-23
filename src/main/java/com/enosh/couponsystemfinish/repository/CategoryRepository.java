package com.enosh.couponsystemfinish.repository;

import com.enosh.couponsystemfinish.model.Category;
import com.enosh.couponsystemfinish.model.CategoryType;
import com.enosh.couponsystemfinish.model.Company;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.Optional;

@Repository
public interface CategoryRepository extends CrudRepository<Category, Long> {

    Optional<Category> findByType(CategoryType type);
}
