package com.hyvercode.springday.repository;

import com.hyvercode.springday.model.entity.ProductCategory;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductCategoryRepository extends PagingAndSortingRepository<ProductCategory,String> {
}
