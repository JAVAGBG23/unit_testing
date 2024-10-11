package com.example.unit_testing.services;

import com.example.unit_testing.models.Product;
import com.example.unit_testing.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

public class ProductServiceTest {
    // mocka
    @Mock
    private ProductRepository productRepository;

    // injecera mocksen
    @InjectMocks
    private ProductService productService;

    // initiera alla mocks
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllProducts() {
        // Arrange
        Product product1 = new Product();
        product1.setId("1");
        product1.setName("Product A");
        product1.setDescription("Description A");
        product1.setColor("Red");
        product1.setPrice(9.99);
        product1.setStockQuantity(100);

        Product product2 = new Product();
        product2.setId("2");
        product2.setName("Product B");
        product2.setDescription("Description B");
        product2.setColor("Blue");
        product2.setPrice(10.99);
        product2.setStockQuantity(200);

        List<Product> productList = Arrays.asList(product1, product2);

        when(productRepository.findAll()).thenReturn(productList);

        // Act
        List<Product> result = productService.getAllProducts();

        // Assert
        assertNotNull(result, "Result should not be null");
        assertEquals(2, result.size(), "There should be 2 products in the list");
        assertEquals("Product A", result.get(0).getName(), "First product name should match");
        assertEquals("Product B", result.get(1).getName(), "Second product name should match");

        verify(productRepository, times(1)).findAll();
    }




}
