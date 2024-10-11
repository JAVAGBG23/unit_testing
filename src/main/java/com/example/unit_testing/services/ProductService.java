package com.example.unit_testing.services;

import com.example.unit_testing.dto.ProductDTO;
import com.example.unit_testing.exceptions.ProductNotFoundException;
import com.example.unit_testing.models.Product;
import com.example.unit_testing.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    /**
     * Retrieves all products from the repository.
     *
     * @return A list of all products.
     */
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    /**
     * Creates a new product based on the provided ProductDTO.
     *
     * @param productDTO The data transfer object containing product details.
     * @return The saved product with an assigned ID.
     */
    public Product createProduct(ProductDTO productDTO) {
        Product product = new Product();
        product.setName(productDTO.getName());
        product.setDescription(productDTO.getDescription());
        product.setColor(productDTO.getColor());
        product.setPrice(productDTO.getPrice());
        product.setStockQuantity(productDTO.getStockQuantity());

        return productRepository.save(product);
    }

    /**
     * Deletes a product by its ID.
     *
     * @param id The ID of the product to delete.
     */
    public void deleteProduct(String id) {
        // check id product exists
       boolean exists = productRepository.existsById(id);

        if(!exists) {
            throw new ProductNotFoundException("Product not found with id: " + id);
        }

        productRepository.deleteById(id);
    }

    /**
     * Retrieves products by their name.
     *
     * @param name The name of the products to retrieve.
     * @return A list of products matching the given name.
     */
    public List<Product> getProductsByName(String name) {
        return productRepository.findByName(name);
    }

    /**
     * Retrieves products within a specified price range.
     *
     * @param minPrice The minimum price.
     * @param maxPrice The maximum price.
     * @return A list of products within the price range.
     */
    public List<Product> getProductsByPriceRange(double minPrice, double maxPrice) {
        return productRepository.findByPriceBetween(minPrice, maxPrice);
    }

    /**
     * Retrieves products by their color.
     *
     * @param color The color of the products to retrieve.
     * @return A list of products matching the given color.
     */
    public List<Product> getProductsByColor(String color) {
        return productRepository.findByColor(color);
    }

    /**
     * Retrieves products by size and material.
     *
     * @param size     The size of the products.
     * @param material The material of the products.
     * @return A list of products matching the size and material.
     */
    public List<Product> getProductsBySizeAndMaterial(String size, String material) {
        return productRepository.findBySizeAndMaterial(size, material);
    }
}
