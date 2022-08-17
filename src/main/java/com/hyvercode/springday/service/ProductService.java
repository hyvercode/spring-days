package com.hyvercode.springday.service;

import com.hyvercode.springday.exception.BusinessException;
import com.hyvercode.springday.helpers.Constant;
import com.hyvercode.springday.model.entity.Product;
import com.hyvercode.springday.model.request.ProductRequest;
import com.hyvercode.springday.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class ProductService {

    private ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> findAll() {
        Iterable<Product> products = productRepository.findAll();
        List<Product> productList = new ArrayList<>();
        products.forEach(productList::add);
        return productList;
    }

    public Product findById(String id) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        if (optionalProduct.isEmpty()) {
            log.info("Record not found {}", id);
            throw new BusinessException("Record not found");
        }

        return optionalProduct.get();
    }

    /**
     * Create
     * @param request
     * @return
     */
    public Product create(ProductRequest request) {
        Product product = Product.builder()
                .sku(request.getSku())
                .productName(request.getProductName())
                .price(request.getPrice())
                .isActive(request.getIsActive())
                .build();
        product.setCreatedBy(Constant.CREATOR);
        product.setCreatedTime(new Timestamp(System.currentTimeMillis()));
        return productRepository.save(product);
    }
}
