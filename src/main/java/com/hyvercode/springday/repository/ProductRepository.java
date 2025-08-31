package com.hyvercode.springday.repository;

import com.hyvercode.springday.model.entity.Product;
import com.hyvercode.springday.model.projection.ProductCategoryView;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends PagingAndSortingRepository<Product,String>, CrudRepository<Product,String> {

  Page<Product> findAll(Specification<Product> specification,Pageable pageable);

  @Query(value = "SELECT product.product_id as productId, product.sku as sku, product.product_name as productName, product.price as price,product.product_category_id as productCategoryId,pc.product_category_name as categoryName " +
    "FROM product product " +
    "JOIN product_category pc on product.product_category_id =pc.product_category_id " +
    "WHERE product.product_id=:productId",nativeQuery = true)
  Optional<ProductCategoryView>findByProductId(@Param("productId") String productId);
}
