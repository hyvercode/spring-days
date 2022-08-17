package com.hyvercode.springday.service;

import com.fasterxml.jackson.databind.util.BeanUtil;
import com.hyvercode.springday.exception.BusinessException;
import com.hyvercode.springday.helpers.Constant;
import com.hyvercode.springday.helpers.base.EmptyResponse;
import com.hyvercode.springday.model.entity.Product;
import com.hyvercode.springday.model.request.ProductRequest;
import com.hyvercode.springday.model.response.ProductResponse;
import com.hyvercode.springday.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    /**
     * Find All
     *
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
                .isActive(product.getIsActive())
                .build()));
        return productResponses;
    }

    /**
     * Find By ID
     *
     * @param id
     * @return
     */
    public ProductResponse findById(String id) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        if (optionalProduct.isEmpty()) {
            log.info(Constant.ERROR_MESSAGE_01 + "{}", id);
            throw new BusinessException(HttpStatus.CONFLICT, Constant.ERROR_CODE_01, Constant.ERROR_MESSAGE_01);
        }
        Product product = optionalProduct.get();

        return ProductResponse.builder()
                .productId(product.getProductId())
                .sku(product.getSku())
                .productName(product.getProductName())
                .price(product.getPrice())
                .isActive(product.getIsActive())
                .build();
    }

    /**
     * Create
     *
     * @param request
     * @return
     */
    public EmptyResponse create(ProductRequest request) {
        Product product = Product.builder()
                .sku(request.getSku())
                .productName(request.getProductName())
                .price(request.getPrice())
                .isActive(request.getIsActive())
                .build();
        product.setCreatedBy(Constant.CREATOR);
        product.setCreatedTime(new Timestamp(System.currentTimeMillis()));
        productRepository.save(product);

        return new EmptyResponse();
    }

    /**
     * Update
     *
     * @param id
     * @param request
     * @return
     */
    public EmptyResponse update(String id, ProductRequest request) {

        Optional<Product> optionalProduct = productRepository.findById(id);
        if (optionalProduct.isEmpty()) {
            log.info(Constant.ERROR_MESSAGE_01 + "{}", id);
            throw new BusinessException(HttpStatus.CONFLICT, Constant.ERROR_CODE_01, Constant.ERROR_MESSAGE_01);
        }
        Product product = optionalProduct.get();
        BeanUtils.copyProperties(request, product);
        product.setUpdatedBy(Constant.CREATOR);
        product.setUpdatedTime(new Timestamp(System.currentTimeMillis()));
        productRepository.save(product);

        return new EmptyResponse();
    }

    /**
     * Delete
     *
     * @param id
     * @return
     */
    public EmptyResponse delete(String id) {

        Optional<Product> optionalProduct = productRepository.findById(id);
        if (optionalProduct.isEmpty()) {
            log.info(Constant.ERROR_MESSAGE_01 + "{}", id);
            throw new BusinessException(HttpStatus.CONFLICT, Constant.ERROR_CODE_01, Constant.ERROR_MESSAGE_01);
        }
        productRepository.deleteById(id);

        return new EmptyResponse();
    }
}
