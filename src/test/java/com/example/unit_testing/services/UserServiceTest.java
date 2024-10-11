package com.example.unit_testing.services;

import com.example.unit_testing.models.User;
import com.example.unit_testing.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserServiceTest {
    // mocka
    @Mock
    private UserRepository userRepository;

    // injecera mocksen
    @InjectMocks
    private UserService userService;

    // initiera alla mocks
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

   // test createUser
    @Test
    public void testCreateUser_success() {
        // Arrange
        // skapa en sample user
        User user = new User();
        user.setFirstName("Janne");
        user.setLastName("Jannesson");
        user.setEmail("janne@gmail.com");

        // skapa en user som ska simulera usern (baserat på ovan user) som vi
        // förväntar oss få tillbara efter att vi har gjort userRepository.save()
        User savedUser = new User();
        savedUser.setId("1");
        savedUser.setFirstName(user.getFirstName());
        savedUser.setLastName(user.getLastName());
        savedUser.setEmail(user.getEmail());

        when(userRepository.save(user)).thenReturn(savedUser);

        // Act
        User result = userService.createUser(user);

        // Assert
        // verifiera att usern blev sparad och att den returnerade usern har ett korrekt id
        assertNotNull(result.getId(), "Saved user should have an ID.");
        assertEquals("Janne", result.getFirstName(), "First name should match.");
        assertEquals("Jannesson", result.getLastName(), "Last name should match.");
        assertEquals("janne@gmail.com", result.getEmail(), "Email should match.");

        verify(userRepository, times(1)).save(user);
    }

    @Test
    public void testGetUserById_UserExists() {
        // Arrange
        // definiera ett user id och skapa en user
        String userId = "1";
        User user = new User();
        user.setId(userId);
        user.setFirstName("Nisse");
        user.setLastName("Jannesson");
        user.setEmail("janne@gmail.com");

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        // Act
        // förväntade resultatet
        User result = userService.getUserById(userId);

        // Assert
        // se till user id inte är null och att fältet matchar
        assertNotNull(result, "Returned user should not be null");
        assertEquals(userId, result.getId(), "User ID should match");
        assertEquals("Nisse", result.getFirstName(), "First name should match.");
        assertEquals("Jannesson", result.getLastName(), "Last name should match.");
        assertEquals("janne@gmail.com", result.getEmail(), "Email should match.");

        verify(userRepository, times(1)).findById(userId);
    }

    @Test
    public void testGetUserById_UserDoesNotExists() {
        // Arrange
        String userId = "nonexistent_id";

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // Act
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            userService.getUserById(userId);
        }, "Expected getUserById to throw, but it didn't");

        // Assert
        assertTrue(exception.getMessage().contains("User not found with id: " + userId));

        verify(userRepository, times(1)).findById(userId);
    }
}




































































