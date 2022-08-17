package com.hyvercode.springday.controller;

import com.hyvercode.springday.helpers.base.EmptyResponse;
import com.hyvercode.springday.model.request.ProductCategoryRequest;
import com.hyvercode.springday.service.ProductCategoryService;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/product-categories")
public class ProductCategoryController {

  private final ProductCategoryService  productCategoryService;

  public ProductCategoryController(ProductCategoryService productCategoryService) {
    this.productCategoryService = productCategoryService;
  }

  @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseBody
  public EmptyResponse postCreateProductCategory(@RequestBody @Validated ProductCategoryRequest request){
    return productCategoryService.create(request);
  }
}
