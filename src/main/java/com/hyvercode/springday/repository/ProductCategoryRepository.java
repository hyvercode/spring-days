package com.hyvercode.springday.repository;

import com.hyvercode.springday.model.entity.ProductCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductCategoryRepository extends PagingAndSortingRepository<ProductCategory,String> {
  Page<ProductCategory> findAll(Specification<ProductCategory> specification, Pageable pageable);
}
