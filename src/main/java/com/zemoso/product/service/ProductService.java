package com.zemoso.product.service;

import com.zemoso.product.entity.Product;
import com.zemoso.product.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;
    public List<Product> getProducts() {
        return productRepository.findAll();
    }

    public void addProduct(Product product) {
        productRepository.save(product);
    }

    public void deleteProduct(Long productId) {
        productRepository.deleteById(productId);
    }

    public Product getProduct(Long productId) {

        return productRepository.findById(productId).orElseThrow(()->new RuntimeException("Product not found of id-"+productId));
    }

    public void updateProduct(Product product) {

        productRepository.save(product);
    }
}
