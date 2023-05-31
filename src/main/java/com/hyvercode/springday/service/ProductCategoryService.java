package com.hyvercode.springday.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.hyvercode.springday.auth.SecurityContextService;
import com.hyvercode.springday.helpers.base.EmptyResponse;
import com.hyvercode.springday.messaging.publisher.RabbitmqPublisher;
import com.hyvercode.springday.model.entity.ProductCategory;
import com.hyvercode.springday.model.request.ProductCategoryRequest;
import com.hyvercode.springday.repository.ProductCategoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.Timestamp;

@Slf4j
@Service
public class ProductCategoryService {

  private final ProductCategoryRepository productCategoryRepository;

  private final SecurityContextService securityContextService;

  private final RabbitmqPublisher rabbitmqPublisher;

  public ProductCategoryService(ProductCategoryRepository productCategoryRepository, SecurityContextService securityContextService, RabbitmqPublisher rabbitmqPublisher) {
    this.productCategoryRepository = productCategoryRepository;
    this.securityContextService = securityContextService;
    this.rabbitmqPublisher = rabbitmqPublisher;
  }

  @Transactional
  public EmptyResponse create(ProductCategoryRequest request) {
    ProductCategory productCategory = ProductCategory.builder()
      .productCategoryName(request.getProductCategoryName())
      .description(request.getDescription())
      .isActive(request.getIsActive())
      .build();
    productCategory.setCreatedBy(securityContextService.getCurrentUserId());
    productCategory.setCreatedTime(new Timestamp(System.currentTimeMillis()));
    var productResult = productCategoryRepository.save(productCategory);

    //Publish
    rabbitmqPublisher.storeAndSend("PRODUCT", productResult);

    return new EmptyResponse();
  }

}
