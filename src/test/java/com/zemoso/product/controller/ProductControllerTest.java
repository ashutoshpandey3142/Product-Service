package com.zemoso.product.controller;

import com.zemoso.product.entity.Product;
import com.zemoso.product.repository.ProductRepository;
import com.zemoso.product.service.ProductService;
import com.zemoso.shoppingcart.model.ShoppingCartRequest;
import com.zemoso.shoppingcart.model.ShoppingCartResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class ProductControllerTest {
    @InjectMocks
    private ProductService productService;

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private RedirectAttributes redirectAttributes;

    @Mock
    private ProductRepository productRepository;
    @Mock
    private Model model;

    private ProductController productController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        productController = new ProductController();
        restTemplate = new RestTemplate();
    }

    @Test
    void showProductList() {
        // Arrange
        List<Product> products = new ArrayList<>();
        products.add(new Product());
        products.add(new Product());
        when(productService.getProducts()).thenReturn(products);

        // Assert
        assertEquals(2,products.size());
    }
}
