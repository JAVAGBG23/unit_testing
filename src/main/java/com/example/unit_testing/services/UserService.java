package com.example.unit_testing.services;

import com.example.unit_testing.models.User;
import com.example.unit_testing.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;


    /**
     * Creates a new user by saving it to the repository.
     *
     * @param user The user to create.
     * @return The saved user with an assigned ID.
     */
    public User createUser(User user){
        System.out.println("hejhej");
        System.out.println("hejhej");

        return userRepository.save(user);
    }

    /**
     * Retrieves a user by their ID.
     *
     * @param id The ID of the user to retrieve.
     * @return The found user.
     * @throws RuntimeException if no user is found with the given ID.
     */
    public User getUserById(String id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            return user.get();
        } else {
            throw new RuntimeException("User not found with id: " + id);
        }
    }
}
