package com.example.unit_testing.services;

import com.example.unit_testing.models.User;
import com.example.unit_testing.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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
        savedUser.setId(user.getEmail());

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
}
















































