package com.hyvercode.springday.controller;

import com.hyvercode.springday.helpers.base.BasePaginationRequest;
import com.hyvercode.springday.helpers.base.EmptyResponse;
import com.hyvercode.springday.model.request.ProductRequest;
import com.hyvercode.springday.model.response.ProductCategoryViewResponse;
import com.hyvercode.springday.model.response.product.PageProductResponse;
import com.hyvercode.springday.model.response.product.ProductResponse;
import com.hyvercode.springday.service.ProductService;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

  private final ProductService productService;

  public ProductController(ProductService productService) {
    this.productService = productService;
  }

  @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseBody
  public List<ProductResponse> getAllProduct() {
    return productService.findAll();
  }

  @GetMapping(value = "/paginate", produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseBody
  public PageProductResponse getPaginateProduct(BasePaginationRequest request) {
    return productService.paginate(request);
  }

  @GetMapping(value = "/{product-id}", produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseBody
  public ProductResponse getProductById(@PathVariable("product-id") String productId) {
    return productService.findById(productId);
  }

  @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseBody
  public EmptyResponse postCreateProduct(@RequestBody @Validated ProductRequest request) {
    return productService.create(request);
  }

  @PutMapping(value = "/{product-id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseBody
  public EmptyResponse putCreateProduct(@PathVariable("product-id") String productId, @RequestBody @Validated ProductRequest request) {
    return productService.update(productId, request);
  }

  @DeleteMapping(value = "/{product-id}", produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseBody
  public EmptyResponse deleteProduct(@PathVariable("product-id") String productId) {
    return productService.delete(productId);
  }

  @GetMapping(value = "/view/{product-id}", produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseBody
  public ProductCategoryViewResponse getProductCategoryByProductId(@PathVariable("product-id") String productId) {
    return productService.findByProductId(productId);
  }
}
