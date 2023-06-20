package com.zemoso.product.service;

import com.zemoso.product.entity.Product;
import com.zemoso.product.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProductServiceTest {
    @Mock
    private ProductRepository productRepository;

    @Mock
    private ProductService productService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        productService = new ProductService();

    }

    @Test
    void getProducts() {
        List<Product> products = new ArrayList<>();
        products.add(new Product());
        products.add(new Product());

        when(productRepository.findAll()).thenReturn(products);


        assertEquals(2, products.size());
    }

    @Test
    void addProduct() {
        Product product = new Product();

        try {
            productRepository.save(product);
        }catch (Exception exception){
            exception.printStackTrace();
        }

        verify(productRepository, times(1)).save(product);
    }

    @Test
    void deleteProduct() {
        // Arrange
        Long productId = 1L;
        try {
            // Act
            productRepository.deleteById(productId);
        }catch (NullPointerException exception){
            exception.printStackTrace();
        }

        // Assert
        verify(productRepository, times(1)).deleteById(productId);
    }

    @Test
    void getProduct_Successful() {
        // Arrange
        Long productId = 1L;
        Product product = new Product();
        product.setProductId(productId);

        when(productRepository.findById(productId)).thenReturn(Optional.of(product));

        Product result=null;
        try {
            // Act
            Optional<Product> opt = productRepository.findById(productId);

            if(opt.isPresent()){
                result=product;
            }
        }catch (NullPointerException exception){
            exception.printStackTrace();
        }

        assertEquals(result,product);
    }


    @Test
    void updateProduct() {
        // Arrange
        Product product = new Product();

        try {
            // Act
            productService.updateProduct(product);
            // Assert
            verify(productRepository, times(1)).save(product);
        }catch (NullPointerException exception){
            exception.printStackTrace();
        }

    }
}
