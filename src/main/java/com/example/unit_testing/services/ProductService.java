package com.example.unit_testing.services;

import com.example.unit_testing.dto.ProductDTO;
import com.example.unit_testing.models.Product;
import com.example.unit_testing.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    /**
     * Creates a new product based on the provided ProductDTO.
     *
     * @param productDTO The data transfer object containing product details.
     * @return The saved product with an assigned ID.
     * @throws IllegalArgumentException if required fields are missing or invalid.
     */
    // ignorera varningen, den kommer pga ingen controller
    public Product createProduct(ProductDTO productDTO) {

        if (productDTO.getName() == null || productDTO.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Product name cannot be null or empty.");
        }
        if (productDTO.getPrice() < 0) {
            throw new IllegalArgumentException("Product price cannot be negative.");
        }
        if (productDTO.getStockQuantity() < 0) {
            throw new IllegalArgumentException("Stock quantity cannot be negative.");
        }

        Product product = new Product();
        product.setName(productDTO.getName());
        product.setDescription(productDTO.getDescription());
        product.setColor(productDTO.getColor());
        product.setPrice(productDTO.getPrice());
        product.setStockQuantity(productDTO.getStockQuantity());

        return productRepository.save(product);
    }

    // version 1
    /*public Product createProduct(ProductDTO productDTO) {
        Product product = new Product();
        product.setName(productDTO.getName());
        product.setDescription(productDTO.getDescription());
        product.setColor(productDTO.getColor());
        product.setPrice(productDTO.getPrice());
        product.setStockQuantity(productDTO.getStockQuantity());

        return productRepository.save(product);
    }*/

    /**
     * Retrieves all products from the repository.
     *
     * @return A list of all products.
     */
    public List<Product> getAllProducts() {
        List<Product> products = productRepository.findAll();
        if (products == null) {
            throw new IllegalArgumentException("Failed to retrieve products.");
        }
        return products;
    }

    // version 1
    /*public List<Product> getAllProducts() {
        return productRepository.findAll();
    }*/

    /**
     * Deletes a product by its ID.
     *
     * @param id The ID of the product to delete.
     * @throws NoSuchElementException if the product does not exist.
     * @throws IllegalArgumentException if the ID is null or empty.
     */
    public void deleteProduct(String id) {
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("Product ID cannot be null or empty.");
        }

        boolean exists = productRepository.existsById(id);
        if (!exists) {
            throw new NoSuchElementException("Product not found with id: " + id);
        }

        productRepository.deleteById(id);
    }

    /**
     * Retrieves products by their name.
     *
     * @param name The name of the products to retrieve.
     * @return A list of products matching the given name.
     * @throws IllegalArgumentException if the name is null or empty.
     * @throws NoSuchElementException if no products match the given name.
     */
    public List<Product> getProductsByName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Product name cannot be null or empty.");
        }

        List<Product> products = productRepository.findByName(name);
        if (products.isEmpty()) {
            throw new NoSuchElementException("No products found with name: " + name);
        }

        return products;
    }

    // version 1
    /*public List<Product> getProductsByName(String name) {
        return productRepository.findByName(name);
    }*/


    /**
     * Retrieves products within a specified price range.
     *
     * @param minPrice The minimum price.
     * @param maxPrice The maximum price.
     * @return A list of products within the price range.
     * @throws IllegalArgumentException if minPrice > maxPrice or any price is negative.
     */
    public List<Product> getProductsByPriceRange(double minPrice, double maxPrice) {
        if (minPrice < 0 || maxPrice < 0) {
            throw new IllegalArgumentException("Price values cannot be negative.");
        }
        if (minPrice > maxPrice) {
            throw new IllegalArgumentException("minPrice cannot be greater than maxPrice.");
        }

        List<Product> products = productRepository.findByPriceBetween(minPrice, maxPrice);
        if (products.isEmpty()) {
            throw new NoSuchElementException("No products found within price range: " + minPrice + " - " + maxPrice);
        }

        return products;
    }

    // version 1
 /*   public List<Product> getProductsByPriceRange(double minPrice, double maxPrice) {
        return productRepository.findByPriceBetween(minPrice, maxPrice);
    }*/

  // version 1
    /*public List<Product> getProductsByColor(String color) {
        return productRepository.findByColor(color);
    }*/

    /**
     * Retrieves products by their color.
     *
     * @param color The color of the products to retrieve.
     * @return A list of products matching the given color.
     * @throws IllegalArgumentException if the color is null or empty.
     * @throws NoSuchElementException if no products match the given color.
     */
    public List<Product> getProductsByColor(String color) {
        if (color == null || color.trim().isEmpty()) {
            throw new IllegalArgumentException("Product color cannot be null or empty.");
        }

        List<Product> products = productRepository.findByColor(color);
        if (products.isEmpty()) {
            throw new NoSuchElementException("No products found with color: " + color);
        }

        return products;
    }
}
