package com.hyvercode.springday.service;

import com.hyvercode.springday.auth.SecurityContextService;
import com.hyvercode.springday.exception.BusinessException;
import com.hyvercode.springday.helpers.constant.ErrorConstant;
import com.hyvercode.springday.helpers.base.BasePaginationRequest;
import com.hyvercode.springday.helpers.base.EmptyResponse;
import com.hyvercode.springday.helpers.utils.PageableUtil;
import com.hyvercode.springday.model.entity.ProductCategory;
import com.hyvercode.springday.model.request.ProductCategoryRequest;
import com.hyvercode.springday.model.response.product_category.PageProductCategoryResponse;
import com.hyvercode.springday.model.response.product_category.ProductCategoryResponse;
import com.hyvercode.springday.repository.ProductCategoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class ProductCategoryService {

  private final ProductCategoryRepository productCategoryRepository;

  private final SecurityContextService securityContextService;

  public ProductCategoryService(ProductCategoryRepository productCategoryRepository,
                                SecurityContextService securityContextService) {
    this.productCategoryRepository = productCategoryRepository;
    this.securityContextService = securityContextService;
  }

  /**
   * @return
   */
  public List<ProductCategoryResponse> all() {
    Iterable<ProductCategory> products = productCategoryRepository.findAll();
    List<ProductCategoryResponse> productResponses = new ArrayList<>();
    products.forEach(product -> productResponses.add(ProductCategoryResponse.builder()
      .productCategoryId(product.getProductCategoryId())
      .productCategoryName(product.getProductCategoryName())
      .isActive(product.getIsActive())
      .build()));
    return productResponses;
  }

  /**
   * @param request
   * @return
   */
  public PageProductCategoryResponse page(BasePaginationRequest request) {
    String sortBy = request.getSortBy() != null && !request.getSortBy().isEmpty() ? request.getSortBy() : "created_time";
    Pageable pageable = PageableUtil.createPageRequest(request, request.getPageSize(), request.getPageNumber(),
      sortBy, request.getSortType());

    Page<ProductCategory> page = productCategoryRepository.findAll((Root<ProductCategory> root, CriteriaQuery<?> query, CriteriaBuilder builder) ->
      builder.and(builder.like(root.get(request.getSearchBy()), '%' + request.getSearchParam() + '%')), pageable);

    List<ProductCategoryResponse> responses = page.getContent().stream().map(item -> {
      ProductCategoryResponse response = new ProductCategoryResponse();
      BeanUtils.copyProperties(item, response);
      return response;
    }).toList();

    return PageProductCategoryResponse.builder()
      .data(responses)
      .pagination(PageableUtil.pageToPagination(page))
      .build();
  }

  /**
   * @param id
   * @return
   */
  public ProductCategoryResponse read(String id) {
    Optional<ProductCategory> productCategoryOptional = productCategoryRepository.findById(id);
    if (productCategoryOptional.isEmpty()) {
      log.info(ErrorConstant.ERROR_MESSAGE_01 + "{}", id);
      throw new BusinessException(HttpStatus.CONFLICT, ErrorConstant.ERROR_CODE_01, ErrorConstant.ERROR_MESSAGE_01);
    }
    var productCategory = productCategoryOptional.get();
    return ProductCategoryResponse.builder()
      .productCategoryId(productCategory.getProductCategoryId())
      .productCategoryName(productCategory.getProductCategoryName())
      .isActive(productCategory.getIsActive())
      .build();
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
    productCategoryRepository.save(productCategory);

    return new EmptyResponse();
  }

  /**
   * @param id
   * @param request
   * @return
   */
  @Transactional
  public EmptyResponse update(String id, ProductCategoryRequest request) {
    Optional<ProductCategory> productCategoryOptional = productCategoryRepository.findById(id);
    if (productCategoryOptional.isEmpty()) {
      log.info(ErrorConstant.ERROR_MESSAGE_01 + "{}", id);
      throw new BusinessException(HttpStatus.CONFLICT, ErrorConstant.ERROR_CODE_01, ErrorConstant.ERROR_MESSAGE_01);
    }

    var productCategory = productCategoryOptional.get();
    BeanUtils.copyProperties(request, productCategory);
    productCategory.setUpdatedBy(securityContextService.getCurrentUserId());
    productCategory.setUpdatedTime(new Timestamp(System.currentTimeMillis()));
    productCategoryRepository.save(productCategory);

    return new EmptyResponse();
  }

  @Transactional
  public EmptyResponse delete(String id) {
    Optional<ProductCategory> productCategoryOptional = productCategoryRepository.findById(id);
    if (productCategoryOptional.isEmpty()) {
      log.info(ErrorConstant.ERROR_MESSAGE_01 + "{}", id);
      throw new BusinessException(HttpStatus.CONFLICT, ErrorConstant.ERROR_CODE_01, ErrorConstant.ERROR_MESSAGE_01);
    }
    productCategoryRepository.deleteById(id);
    return new EmptyResponse();
  }

}
