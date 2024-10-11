package com.example.unit_testing.repository;

import com.example.unit_testing.models.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {
}

