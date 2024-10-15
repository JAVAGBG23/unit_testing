package com.example.unit_testing.controllers;

import com.example.unit_testing.models.Product;
import com.example.unit_testing.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ProductControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProductRepository productRepository;

    @BeforeEach
    void cleanDatabase() {
        productRepository.deleteAll();
    }

    @Test
    public void testCreateProduct() throws Exception {
        String productJson = """
    {
    "name": "Test Product",
    "description": "A test product",
    "price": 99.99,
    "stockQuantity": 50
        }
    """;

      // gör en POST request och skickar till controllern
      mockMvc.perform(post("/api/products")
              .contentType(MediaType.APPLICATION_JSON)
              .content(productJson))
              .andExpect(status().isCreated());


        List<Product> products = productRepository.findAll();
        assertThat(products).hasSize(1);
        assertThat(products.get(0).getName()).isEqualTo("Test Product");
    }

    @Test
    public void testGetProductsByName() throws Exception {
        Product product = new Product();
        product.setName("Test Product");
        product.setDescription("Description for Test Product");
        product.setPrice(99.99);

        productRepository.save(product);

        MvcResult result = mockMvc.perform(get("/api/products/name/Test Product")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String jsonResponse = result.getResponse().getContentAsString();
        assertThat(jsonResponse).contains("Test Product");

    }

    @Test
    public void testDeleteProduct() throws Exception {
        Product product = new Product();
        product.setName("Product to Delete");
        product.setDescription("Will be deleted");
        product.setPrice(9.99);
        product.setStockQuantity(50);

        Product savedProduct = productRepository.save(product);

        mockMvc.perform(delete("/api/products/" + savedProduct.getId()))
                .andExpect(status().isNoContent());

        assertThat(productRepository.findById(savedProduct.getId())).isEmpty();
    }







}










