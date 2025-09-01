package controller;

import com.github.users.controller.UserController;
import com.github.users.model.entity.User;
import com.github.users.repository.UserRepository;
import com.github.users.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {


    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @Test
    void testCreateUsers() {
        List<User> usuariosFalsos = List.of(
                new User("jose", "https://api.github.com/users/jose"),
                new User("eloa", "https://api.github.com/users/eloa")
        );

        when(userService.createUsers()).thenReturn(usuariosFalsos);

        ResponseEntity<List<User>> response = userController.createUsers();

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(2, response.getBody().size());
        verify(userService, times(1)).createUsers();
    }
}
