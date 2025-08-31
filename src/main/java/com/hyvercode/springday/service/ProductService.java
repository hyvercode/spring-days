package com.hyvercode.springday.service;

import com.hyvercode.springday.auth.SecurityContextService;
import com.hyvercode.springday.exception.BusinessException;
import com.hyvercode.springday.helpers.constant.ErrorConstant;
import com.hyvercode.springday.helpers.base.BasePaginationRequest;
import com.hyvercode.springday.helpers.base.EmptyResponse;
import com.hyvercode.springday.helpers.utils.PageableUtil;
import com.hyvercode.springday.messaging.publisher.RabbitmqPublisher;
import com.hyvercode.springday.model.dto.ProductCategoryDto;
import com.hyvercode.springday.model.dto.ProductInventoryDto;
import com.hyvercode.springday.model.entity.Product;
import com.hyvercode.springday.model.entity.ProductCategory;
import com.hyvercode.springday.model.entity.ProductInventory;
import com.hyvercode.springday.model.projection.ProductCategoryView;
import com.hyvercode.springday.model.request.ProductRequest;
import com.hyvercode.springday.model.response.ProductCategoryViewResponse;
import com.hyvercode.springday.model.response.product.PageProductResponse;
import com.hyvercode.springday.model.response.product.ProductResponse;
import com.hyvercode.springday.repository.ProductCategoryRepository;
import com.hyvercode.springday.repository.ProductInventoryRepository;
import com.hyvercode.springday.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import jakarta.transaction.Transactional;
import java.sql.Timestamp;
import java.util.*;

@Slf4j
@Service
public class ProductService {

  private final ProductRepository productRepository;
  private final ProductCategoryRepository productCategoryRepository;

  private final ProductInventoryRepository productInventoryRepository;

  private final SecurityContextService securityContextService;

  private final RabbitmqPublisher rabbitmqPublisher;

  public ProductService(ProductRepository productRepository, ProductCategoryRepository productCategoryRepository, ProductInventoryRepository productInventoryRepository, SecurityContextService securityContextService, RabbitmqPublisher rabbitmqPublisher) {
    this.productRepository = productRepository;
    this.productCategoryRepository = productCategoryRepository;
    this.productInventoryRepository = productInventoryRepository;
    this.securityContextService = securityContextService;
    this.rabbitmqPublisher = rabbitmqPublisher;
  }

  /**
   * Find All
   * @return
   */
  public List<ProductResponse> findAll() {
    Iterable<Product> products = productRepository.findAll();
    List<ProductResponse> productResponses = new ArrayList<>();
    products.forEach(product -> productResponses.add(ProductResponse.builder()
      .productId(product.getProductId())
      .sku(product.getSku())
      .productName(product.getProductName())
      .price(product.getPrice())
      .productCategory(ProductCategoryDto.builder()
        .productCategoryId(product.getProductCategory().getProductCategoryId())
        .productCategoryName(product.getProductCategory().getProductCategoryName())
        .description(product.getProductCategory().getDescription())
        .build())
      .productInventory(ProductInventoryDto.builder()
        .productInventoryId(product.getProductInventory().getProductInventoryId())
        .quantity(product.getProductInventory().getQuantity())
        .build())
      .isActive(product.getIsActive())
      .build()));
    return productResponses;
  }

  /**
   * Pagination
   * @param request
   * @return
   */
  public PageProductResponse paginate(BasePaginationRequest request) {
    String sortBy = request.getSortBy() != null && !request.getSortBy().isEmpty() ? request.getSortBy() : "created_time";
    Pageable pageable = PageableUtil.createPageRequest(request, request.getPageSize(), request.getPageNumber(),
      sortBy, request.getSortType());

    Page<Product> page = productRepository.findAll((Root<Product> root, CriteriaQuery<?> query, CriteriaBuilder builder) ->
      builder.and(builder.like(root.get(request.getSearchBy()), '%' + request.getSearchParam() + '%')), pageable);

    List<ProductResponse> responses = page.getContent().stream().map(item -> {
      ProductResponse response = new ProductResponse();
      BeanUtils.copyProperties(item, response);
      response.setProductCategory(ProductCategoryDto.builder()
        .productCategoryId(item.getProductCategory().getProductCategoryId())
        .productCategoryName(item.getProductCategory().getProductCategoryName())
        .description(item.getProductCategory().getDescription())
        .build());
      response.setProductInventory(ProductInventoryDto.builder()
        .productInventoryId(item.getProductInventory().getProductInventoryId())
        .quantity(item.getProductInventory().getQuantity())
        .build());
      return response;
    }).toList();

    return PageProductResponse.builder()
      .data(responses)
      .pagination(PageableUtil.pageToPagination(page))
      .build();
  }

  /**
   * Find By ID
   * @param id
   * @return
   */
  public ProductResponse findById(String id) {
    Optional<Product> optionalProduct = productRepository.findById(id);
    if (optionalProduct.isEmpty()) {
      log.info(ErrorConstant.ERROR_MESSAGE_01 + "{}", id);
      throw new BusinessException(HttpStatus.CONFLICT, ErrorConstant.ERROR_CODE_01, ErrorConstant.ERROR_MESSAGE_01);
    }
    Product product = optionalProduct.get();

    return ProductResponse.builder()
      .productId(product.getProductId())
      .sku(product.getSku())
      .productName(product.getProductName())
      .price(product.getPrice())
      .productCategory(ProductCategoryDto.builder()
        .productCategoryId(product.getProductCategory().getProductCategoryId())
        .productCategoryName(product.getProductCategory().getProductCategoryName())
        .description(product.getProductCategory().getDescription())
        .build())
      .productInventory(ProductInventoryDto.builder()
        .productInventoryId(product.getProductInventory().getProductInventoryId())
        .quantity(product.getProductInventory().getQuantity())
        .build())
      .isActive(product.getIsActive())
      .build();
  }

  /**
   * Create
   * @param request
   * @return
   */
  @Transactional
  public EmptyResponse create(ProductRequest request) {
    Optional<ProductCategory> optionalProductCategory = productCategoryRepository.findById(request.getProductCategoryId());
    ProductCategory productCategory = optionalProductCategory.orElseThrow(() ->
      new BusinessException(HttpStatus.CONFLICT, ErrorConstant.ERROR_CODE_01, ErrorConstant.ERROR_MESSAGE_01));

    ProductInventory productInventory = ProductInventory.builder()
      .quantity(request.getStock())
      .build();
    productInventory.setCreatedBy(securityContextService.getCurrentUserId());
    productInventory.setCreatedTime(new Timestamp(System.currentTimeMillis()));
    var productInventorySave = productInventoryRepository.save(productInventory);

    Product product = Product.builder()
      .sku(request.getSku())
      .productName(request.getProductName())
      .price(request.getPrice())
      .productCategory(productCategory)
      .productInventory(productInventorySave)
      .isActive(request.getIsActive())
      .build();
    product.setCreatedBy(securityContextService.getCurrentUserId());
    product.setCreatedTime(new Timestamp(System.currentTimeMillis()));
    var productResult = productRepository.save(product);

    try {
      rabbitmqPublisher.storeAndSend("PRODUCT", productResult);
    } catch (Exception e) {
      log.info(e.getMessage());
    }
    return new EmptyResponse();
  }

  /**
   * Update
   * @param id
   * @param request
   * @return
   */

  @Transactional
  public EmptyResponse update(String id, ProductRequest request) {

    Optional<Product> optionalProduct = productRepository.findById(id);
    if (optionalProduct.isEmpty()) {
      log.info(ErrorConstant.ERROR_MESSAGE_01 + "{}", id);
      throw new BusinessException(HttpStatus.CONFLICT, ErrorConstant.ERROR_CODE_01, ErrorConstant.ERROR_MESSAGE_01);
    }

    Product product = optionalProduct.get();
    product.setProductName(request.getProductName());
    product.setPrice(request.getPrice());
    product.setIsActive(request.getIsActive());
    product.setUpdatedBy(securityContextService.getCurrentUserId());
    product.setUpdatedTime(new Timestamp(System.currentTimeMillis()));
    productRepository.save(product);

    return new EmptyResponse();
  }

  /**
   * Delete
   * @param id
   * @return
   */
  public EmptyResponse delete(String id) {
    Optional<Product> optionalProduct = productRepository.findById(id);
    if (optionalProduct.isEmpty()) {
      log.info(ErrorConstant.ERROR_MESSAGE_01 + "{}", id);
      throw new BusinessException(HttpStatus.CONFLICT, ErrorConstant.ERROR_CODE_01, ErrorConstant.ERROR_MESSAGE_01);
    }
    productRepository.delete(optionalProduct.get());
    return new EmptyResponse();
  }

  /**
   * @param id
   * @return
   */
  public ProductCategoryViewResponse findByProductId(String id) {
    Optional<ProductCategoryView> optionalProduct = productRepository.findByProductId(id);
    if (optionalProduct.isEmpty()) {
      log.info(ErrorConstant.ERROR_MESSAGE_01 + "{}", id);
      throw new BusinessException(HttpStatus.CONFLICT, ErrorConstant.ERROR_CODE_01, ErrorConstant.ERROR_MESSAGE_01);
    }
    ProductCategoryView product = optionalProduct.get();

    return ProductCategoryViewResponse.builder()
      .productId(product.getProductId())
      .productName(product.getProductName())
      .sku(product.getSku())
      .productCategoryId(product.getProductCategoryId())
      .productCategoryName(product.getProductCategoryName())
      .price(product.getPrice())
      .isActive(product.getIsActive())
      .build();
  }
}
