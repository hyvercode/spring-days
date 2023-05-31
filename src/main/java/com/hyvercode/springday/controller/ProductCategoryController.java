package com.hyvercode.springday.controller;

import com.hyvercode.springday.helpers.base.BasePaginationRequest;
import com.hyvercode.springday.helpers.base.EmptyResponse;
import com.hyvercode.springday.model.request.ProductCategoryRequest;
import com.hyvercode.springday.model.response.product_category.PageProductCategoryResponse;
import com.hyvercode.springday.model.response.product_category.ProductCategoryResponse;
import com.hyvercode.springday.service.ProductCategoryService;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product-categories")
public class ProductCategoryController {

  private final ProductCategoryService productCategoryService;

  public ProductCategoryController(ProductCategoryService productCategoryService) {
    this.productCategoryService = productCategoryService;
  }

  @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseBody
  public List<ProductCategoryResponse> getAllProductCategory() {
    return productCategoryService.all();
  }

  @GetMapping(value = "/paginate", produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseBody
  public PageProductCategoryResponse getPaginateProductCategory(BasePaginationRequest request) {
    return productCategoryService.page(request);
  }

  @GetMapping(value = "/{product-category-id}", produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseBody
  public ProductCategoryResponse getProductCategoryById(@PathVariable("product-category-id") String productCategoryId) {
    return productCategoryService.read(productCategoryId);
  }

  @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseBody
  public EmptyResponse postCreateProductCategory(@RequestBody @Validated ProductCategoryRequest request) {
    return productCategoryService.create(request);
  }

  @PutMapping(value = "/{product-category-id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseBody
  public EmptyResponse putCreateProductCategory(@PathVariable("product-category-id") String productCategoryId,
                                                @RequestBody @Validated ProductCategoryRequest request) {
    return productCategoryService.update(productCategoryId, request);
  }

  @DeleteMapping(value = "/{product-category-id}", produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseBody
  public EmptyResponse deleteProductCategory(@PathVariable("product-category-id") String productCategoryId) {
    return productCategoryService.delete(productCategoryId);
  }
}
