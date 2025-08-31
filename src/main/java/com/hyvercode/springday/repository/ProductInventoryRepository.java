package com.hyvercode.springday.repository;

import com.hyvercode.springday.model.entity.ProductInventory;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductInventoryRepository extends PagingAndSortingRepository<ProductInventory,String>, CrudRepository<ProductInventory,String> {
}
