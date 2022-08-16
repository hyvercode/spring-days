package com.hyvercode.springday.controller;

import com.hyvercode.springday.model.entity.Product;
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
    public List<Product> getAll() {
        return productService.findAll();
    }

    @GetMapping(value = "/{product-id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Product getById(@PathVariable("product-id") String productId) {
        return productService.findById(productId);
    }

    @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Product postCreate(@RequestBody @Validated Product product) {
        return productService.create(product);
    }
}
