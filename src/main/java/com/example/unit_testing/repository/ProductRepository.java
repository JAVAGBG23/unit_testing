package com.example.unit_testing.repository;

import com.example.unit_testing.models.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ProductRepository extends MongoRepository<Product, String> {
    List<Product> findByName(String name);

    List<Product> findByPriceBetween(double minPrice, double maxPrice);

    List<Product> findByColor(String color);

}

