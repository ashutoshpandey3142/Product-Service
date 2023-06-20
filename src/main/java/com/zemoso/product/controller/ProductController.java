package com.zemoso.product.controller;

import com.zemoso.product.entity.Product;
import com.zemoso.product.service.ProductService;
import com.zemoso.shoppingcart.model.ShoppingCartRequest;
import com.zemoso.shoppingcart.model.ShoppingCartResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/products")
@RefreshScope
public class ProductController {
    @Autowired
    private ProductService productService;

    @Value("${product.message}")
    String message;

    @Autowired
    private RestTemplate restTemplate; // Autowire RestTemplate for making HTTP requests
    @GetMapping("/addToCart/{productId}")
    public String addToCart(@PathVariable Long productId, RedirectAttributes redirectAttributes) {
        try {
            Product product = productService.getProduct(productId);

            if(product.getQuantity()>0){
                product.setQuantity(product.getQuantity()-1);
                productService.updateProduct(product);
            }else{
                return "error-page";
            }
            // Prepare the request body
            ShoppingCartRequest request = ShoppingCartRequest.builder().build();
            request.setProductId(product.getProductId());
            request.setQuantity(1); // Assuming a quantity of 1 for simplicity
            request.setAmount(product.getAmount());
            request.setItemName(product.getProductName());


            // Make an HTTP POST request to the shopping cart microservice
            String shoppingCartUrl = "http://localhost:8888/shoppingCart/" + productId;
            restTemplate.postForObject(shoppingCartUrl, request, ShoppingCartResponse.class);

            // Cart updated successfully
            redirectAttributes.addFlashAttribute("successMessage", "Product added to cart successfully");
        } catch (Exception e) {
            // Failed to update the cart
            // Handle the error scenario
            redirectAttributes.addFlashAttribute("errorMessage", "Failed to update the cart");
        }

        return "redirect:/products/list";
    }


    @GetMapping("/list")
    public String showProductList(Model model) {
        List<Product> products = productService.getProducts();
        model.addAttribute("products", products);
        model.addAttribute("message", message);
        return "product-list";
    }

    @GetMapping("/showForm")
    public String showAddProductForm(Model model) {
        Product product = new Product();
        model.addAttribute("product", product);
        return "add-product-form";
    }

    @PostMapping("/add")
    public String addProduct(@ModelAttribute("product") Product product) {
        productService.addProduct(product);
        return "redirect:/products/list";
    }

    @GetMapping("/delete/{productId}")
    public String deleteProduct(@PathVariable Long productId) {
        productService.deleteProduct(productId);
        return "redirect:/products/list";
    }

    @GetMapping("/update/{productId}")
    public String showUpdateProductForm(@PathVariable Long productId, Model model) {
        Product product = productService.getProduct(productId);
        model.addAttribute("product", product);
        return "add-product-form";
    }

    @PostMapping("/update")
    public String updateProduct(@ModelAttribute("product") Product product) {
        productService.updateProduct(product);
        return "redirect:/products/list";
    }

}
