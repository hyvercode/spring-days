package com.hyvercode.springday.service;

import com.hyvercode.springday.model.entity.Product;
import com.hyvercode.springday.repository.ProductRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> findAll(){
        Iterable<Product>products = productRepository.findAll();
        List<Product>productList = new ArrayList<>();
        products.forEach(productList::add);
        return productList;
    }

    public Product findById(String id){
        Optional<Product> optionalProduct = productRepository.findById(id);
        if(optionalProduct.isEmpty()){
            System.out.println("Record not found");
        }

        return optionalProduct.get();
    }

    public Product create(Product product){
        return productRepository.save(product);
    }
}
