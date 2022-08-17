package com.hyvercode.springday.repository;

import com.hyvercode.springday.model.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends PagingAndSortingRepository<Product,String> {

  Page<Product> findAll(Specification<Product> specification,Pageable pageable);
}
