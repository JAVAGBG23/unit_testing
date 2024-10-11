package com.example.unit_testing.services;

import com.example.unit_testing.dto.ProductDTO;
import com.example.unit_testing.models.Product;
import com.example.unit_testing.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;
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

    /**
     * Test the createProduct method to ensure it creates and saves a product correctly.
     */
    @Test
    public void testCreateProduct_Success() {
        // Arrange:
        // create a sample ProductDTO without an ID
        ProductDTO productDTO = new ProductDTO();
        productDTO.setName("Product C");
        productDTO.setDescription("Description C");
        productDTO.setColor("Green");
        productDTO.setPrice(30.99);
        productDTO.setStockQuantity(300);

        // create a Product object that would be saved (without ID)
        Product productToSave = new Product();
        productToSave.setName(productDTO.getName());
        productToSave.setDescription(productDTO.getDescription());
        productToSave.setColor(productDTO.getColor());
        productToSave.setPrice(productDTO.getPrice());
        productToSave.setStockQuantity(productDTO.getStockQuantity());

        // create a Product object that represents the saved product (with ID)
        Product savedProduct = new Product();
        savedProduct.setId("3");
        savedProduct.setName(productDTO.getName());
        savedProduct.setDescription(productDTO.getDescription());
        savedProduct.setColor(productDTO.getColor());
        savedProduct.setPrice(productDTO.getPrice());
        savedProduct.setStockQuantity(productDTO.getStockQuantity());

        // mock the behavior of productRepository.save to return the savedProduct
        when(productRepository.save(any(Product.class))).thenReturn(savedProduct);

        // Act:
        // call the createProduct method in ProductService
        Product result = productService.createProduct(productDTO);

        // Assert:
        // verify that the product was saved correctly
        assertNotNull(result.getId(), "Saved product should have an ID");
        assertEquals("Product C", result.getName(), "Product name should match");
        assertEquals("Description C", result.getDescription(), "Product description should match");
        assertEquals("Green", result.getColor(), "Product color should match");
        assertEquals(30.99, result.getPrice(), "Product price should match");
        assertEquals(300, result.getStockQuantity(), "Product stock quantity should match");

        // verify that productRepository.save was called once with a Product object
        verify(productRepository, times(1)).save(any(Product.class));
    }

    /**
     * Negative Test: createProduct with negative price should throw IllegalArgumentException.
     */
    @Test
    public void testCreateProduct_NegativePrice() {
        // Arrange
        ProductDTO productDTO = new ProductDTO();
        productDTO.setName("Test Product");
        productDTO.setDescription("Test Description");
        productDTO.setColor("Blue");
        productDTO.setPrice(-20.0); // invalid price!
        productDTO.setStockQuantity(10);

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            productService.createProduct(productDTO);
        });

        assertEquals("Product price cannot be negative.", exception.getMessage());

        // Verify
        verify(productRepository, never()).save(any(Product.class));
    }

    /**
     * Negative Test: createProduct with missing name should throw IllegalArgumentException.
     */
    @Test
    public void testCreateProduct_MissingName() {
        // Arrange
        ProductDTO productDTO = new ProductDTO();
        productDTO.setDescription("No name product");
        productDTO.setColor("Red");
        productDTO.setPrice(15.0);
        productDTO.setStockQuantity(5);

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            productService.createProduct(productDTO);
        });

        assertEquals("Product name cannot be null or empty.", exception.getMessage());

        // Verify
        verify(productRepository, never()).save(any(Product.class));
    }

    /**
     * Test the getAllProducts method to ensure it retrieves all products correctly.
     */
    @Test
    public void testGetAllProducts() {
        // Arrange: Create a list of sample products
        Product product1 = new Product();
        product1.setId("1");
        product1.setName("Product A");
        product1.setDescription("Description A");
        product1.setColor("Red");
        product1.setPrice(10.99);
        product1.setStockQuantity(100);

        Product product2 = new Product();
        product2.setId("2");
        product2.setName("Product B");
        product2.setDescription("Description B");
        product2.setColor("Blue");
        product2.setPrice(20.99);
        product2.setStockQuantity(200);

        List<Product> productList = Arrays.asList(product1, product2);

        // mock the behavior of productRepository.findAll to return the product list
        when(productRepository.findAll()).thenReturn(productList);

        // Act:
        // call the getAllProducts method
        List<Product> result = productService.getAllProducts();

        // Assert:
        // verify that the returned list matches the mocked list
        assertNotNull(result, "The result should not be null");
        assertEquals(2, result.size(), "There should be two products in the list");
        assertEquals("Product A", result.get(0).getName(), "First product name should match");
        assertEquals("Product B", result.get(1).getName(), "Second product name should match");

        // verify that productRepository.findAll was called once
        verify(productRepository, times(1)).findAll();
    }

    /**
     * Test the deleteProduct method to ensure it deletes a product by ID.
     */
    @Test
    public void testDeleteProduct_Success() {
        // Arrange:
        // define a product ID to delete
        String productId = "1";

        // mock the behavior of productRepository.deleteById (void method)
        doNothing().when(productRepository).deleteById(productId);

        // Act:
        // call the deleteProduct method
        productService.deleteProduct(productId);

        // Assert:
        // verify that deleteById was called once with the correct ID
        verify(productRepository, times(1)).deleteById(productId);
    }

    /**
     * Negative Test: deleteProduct with non-existent ID should throw NoSuchElementException.
     */
    @Test
    public void testDeleteProduct_NonExistentId() {
        // Arrange
        String nonExistentId = "nonexistent123";

        when(productRepository.existsById(nonExistentId)).thenReturn(false);

        // Act & Assert
        NoSuchElementException exception = assertThrows(NoSuchElementException.class, () -> {
            productService.deleteProduct(nonExistentId);
        });

        assertEquals("Product not found with id: " + nonExistentId, exception.getMessage());

        // Verify
        verify(productRepository, times(1)).existsById(nonExistentId);
        verify(productRepository, never()).deleteById(anyString());
    }

    /**
     * Test the getProductsByName method to ensure it retrieves products by name.
     */
    @Test
    public void testGetProductsByName() {
        // Arrange:
        // define a product name and create sample products
        String productName = "Product A";
        Product product1 = new Product();
        product1.setId("1");
        product1.setName(productName);
        product1.setDescription("Description A");
        product1.setColor("Red");
        product1.setPrice(10.99);
        product1.setStockQuantity(100);

        List<Product> products = Arrays.asList(product1);

        // mock the behavior of productRepository.findByName to return the products list
        when(productRepository.findByName(productName)).thenReturn(products);

        // Act:
        // call the getProductsByName method
        List<Product> result = productService.getProductsByName(productName);

        // Assert:
        // verify that the returned list matches the mocked list
        assertNotNull(result, "The result should not be null");
        assertEquals(1, result.size(), "There should be one product in the list");
        assertEquals(productName, result.get(0).getName(), "Product name should match");

        // verify that findByName was called once with the correct name
        verify(productRepository, times(1)).findByName(productName);
    }

    /**
     * Negative Test: getProductsByName with non-existent name should throw NoSuchElementException.
     */
    @Test
    public void testGetProductsByName_NoMatches() {
        // Arrange
        String nonExistentName = "UnknownProduct";

        when(productRepository.findByName(nonExistentName)).thenReturn(Collections.emptyList());

        // Act & Assert
        NoSuchElementException exception = assertThrows(NoSuchElementException.class, () -> {
            productService.getProductsByName(nonExistentName);
        });

        assertEquals("No products found with name: " + nonExistentName, exception.getMessage());

        // Verify
        verify(productRepository, times(1)).findByName(nonExistentName);
    }

    /**
     * Negative Test: getProductsByName with null name should throw IllegalArgumentException.
     */
    @Test
    public void testGetProductsByName_NullName() {
        // Arrange
        String name = null;

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            productService.getProductsByName(name);
        });

        assertEquals("Product name cannot be null or empty.", exception.getMessage());

        // Verify
        verify(productRepository, never()).findByName(anyString());
    }


    /**
     * Test the getProductsByPriceRange method to ensure it retrieves products within a price range.
     */
    @Test
    public void testGetProductsByPriceRange() {
        // Arrange:
        // define price range and create sample products
        double minPrice = 10.00;
        double maxPrice = 30.00;

        Product product1 = new Product();
        product1.setId("1");
        product1.setName("Product A");
        product1.setDescription("Description A");
        product1.setColor("Red");
        product1.setPrice(15.99);
        product1.setStockQuantity(100);

        Product product2 = new Product();
        product2.setId("2");
        product2.setName("Product B");
        product2.setDescription("Description B");
        product2.setColor("Blue");
        product2.setPrice(25.99);
        product2.setStockQuantity(200);

        List<Product> products = Arrays.asList(product1, product2);

        // mock the behavior of productRepository.findByPriceBetween to return the products list
        when(productRepository.findByPriceBetween(minPrice, maxPrice)).thenReturn(products);

        // act: Call the getProductsByPriceRange method
        List<Product> result = productService.getProductsByPriceRange(minPrice, maxPrice);

        // assert: Verify that the returned list matches the mocked list
        assertNotNull(result, "The result should not be null");
        assertEquals(2, result.size(), "There should be two products in the list");
        assertTrue(result.stream().allMatch(p -> p.getPrice() >= minPrice && p.getPrice() <= maxPrice),
                "All products should be within the specified price range");

        // verify that findByPriceBetween was called once with the correct parameters
        verify(productRepository, times(1)).findByPriceBetween(minPrice, maxPrice);
    }

    /**
     * Negative Test: getProductsByPriceRange with minPrice > maxPrice should throw IllegalArgumentException.
     */
    @Test
    public void testGetProductsByPriceRange_InvalidRange() {
        // Arrange
        double minPrice = 100.0;
        double maxPrice = 50.0;

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            productService.getProductsByPriceRange(minPrice, maxPrice);
        });

        assertEquals("minPrice cannot be greater than maxPrice.", exception.getMessage());

        // Verify
        verify(productRepository, never()).findByPriceBetween(anyDouble(), anyDouble());
    }

    /**
     * Negative Test: getProductsByPriceRange with negative minPrice should throw IllegalArgumentException.
     */
    @Test
    public void testGetProductsByPriceRange_NegativeMinPrice() {
        // Arrange
        double minPrice = -10.0;
        double maxPrice = 50.0;

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            productService.getProductsByPriceRange(minPrice, maxPrice);
        });

        assertEquals("Price values cannot be negative.", exception.getMessage());

        // Verify
        verify(productRepository, never()).findByPriceBetween(anyDouble(), anyDouble());
    }

    /**
     * Negative Test: getProductsByPriceRange with no products in range should throw NoSuchElementException.
     */
    @Test
    public void testGetProductsByPriceRange_NoMatches() {
        // Arrange
        double minPrice = 10.0;
        double maxPrice = 20.0;

        when(productRepository.findByPriceBetween(minPrice, maxPrice)).thenReturn(Collections.emptyList());

        // Act & Assert
        NoSuchElementException exception = assertThrows(NoSuchElementException.class, () -> {
            productService.getProductsByPriceRange(minPrice, maxPrice);
        });

        assertEquals("No products found within price range: " + minPrice + " - " + maxPrice, exception.getMessage());

        // Verify
        verify(productRepository, times(1)).findByPriceBetween(minPrice, maxPrice);
    }

    /**
     * Test the getProductsByColor method to ensure it retrieves products by color.
     */

    @Test
    public void testGetProductsByColor() {
        // Arrange:
        // define a color and create sample products
        String color = "Red";

        Product product1 = new Product();
        product1.setId("1");
        product1.setName("Product A");
        product1.setDescription("Description A");
        product1.setColor(color);
        product1.setPrice(10.99);
        product1.setStockQuantity(100);

        Product product2 = new Product();
        product2.setId("2");
        product2.setName("Product B");
        product2.setDescription("Description B");
        product2.setColor(color);
        product2.setPrice(20.99);
        product2.setStockQuantity(200);

        List<Product> products = Arrays.asList(product1, product2);

        // mock the behavior of productRepository.findByColor to return the products list
        when(productRepository.findByColor(color)).thenReturn(products);

        // Act:
        // call the getProductsByColor method
        List<Product> result = productService.getProductsByColor(color);

        // Assert:
        // verify that the returned list matches the mocked list
        assertNotNull(result, "The result should not be null");
        assertEquals(2, result.size(), "There should be two products in the list");
        assertTrue(result.stream().allMatch(p -> p.getColor().equals(color)),
                "All products should have the specified color");

        // verify that findByColor was called once with the correct color
        verify(productRepository, times(1)).findByColor(color);
    }

    /**
     * Negative Test: getProductsByColor with non-existent color should throw NoSuchElementException.
     */
    @Test
    public void testGetProductsByColor_NoMatches() {
        // Arrange
        String color = "InvisibleColor";

        when(productRepository.findByColor(color)).thenReturn(Collections.emptyList());

        // Act & Assert
        NoSuchElementException exception = assertThrows(NoSuchElementException.class, () -> {
            productService.getProductsByColor(color);
        });

        assertEquals("No products found with color: " + color, exception.getMessage());

        // Verify
        verify(productRepository, times(1)).findByColor(color);
    }

    /**
     * Negative Test: getProductsByColor with null color should throw IllegalArgumentException.
     */
    @Test
    public void testGetProductsByColor_NullColor() {
        // Arrange
        String color = null;

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            productService.getProductsByColor(color);
        });

        assertEquals("Product color cannot be null or empty.", exception.getMessage());

        // Verify
        verify(productRepository, never()).findByColor(anyString());
    }

    /**
     * Negative Test: getProductsByColor with empty color should throw IllegalArgumentException.
     */
    @Test
    public void testGetProductsByColor_EmptyColor() {
        // Arrange
        String color = "   ";

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            productService.getProductsByColor(color);
        });

        assertEquals("Product color cannot be null or empty.", exception.getMessage());

        // Verify
        verify(productRepository, never()).findByColor(anyString());
    }




    // version 1 av tester ligger här under
    /* @Test
    public void testDeleteProduct_success() {
        // Arrange
        String productId = "1";

        doNothing().when(productRepository).deleteById(productId);

        // Act
        productService.deleteProduct(productId);

        // Assert
        verify(productRepository, times(1)).deleteById(productId);
    }*/

     /* @Test
    public void testGetAllProducts_NullResponse() {
        // Arrange
        when(productRepository.findAll()).thenReturn(null);

        // Act
        InvalidProductException exception = assertThrows(InvalidProductException.class, () -> {
            productService.getAllProducts();
        });

        // Assert
        assertEquals("Failed to retrieve products", exception.getMessage());
        verify(productRepository, times(1)).findAll();
    }*/

}











