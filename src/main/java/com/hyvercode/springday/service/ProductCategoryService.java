package com.hyvercode.springday.service;

import com.hyvercode.springday.auth.SecurityContextService;
import com.hyvercode.springday.helpers.base.EmptyResponse;
import com.hyvercode.springday.model.entity.ProductCategory;
import com.hyvercode.springday.model.request.ProductCategoryRequest;
import com.hyvercode.springday.repository.ProductCategoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

@Slf4j
@Service
public class ProductCategoryService {

  private final ProductCategoryRepository productCategoryRepository;

  private final SecurityContextService securityContextService;

  public ProductCategoryService(ProductCategoryRepository productCategoryRepository, SecurityContextService securityContextService) {
    this.productCategoryRepository = productCategoryRepository;
    this.securityContextService = securityContextService;
  }

  public EmptyResponse create(ProductCategoryRequest request){
    ProductCategory productCategory = ProductCategory.builder()
      .productCategoryName(request.getProductCategoryName())
      .description(request.getDescription())
      .isActive(request.getIsActive())
      .build();
    productCategory.setCreatedBy(securityContextService.getCurrentUserId());
    productCategory.setCreatedTime(new Timestamp(System.currentTimeMillis()));
    productCategoryRepository.save(productCategory);

    return new EmptyResponse();
  }
}
