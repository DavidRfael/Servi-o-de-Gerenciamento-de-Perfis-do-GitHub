package service;

import com.github.users.model.entity.User;
import com.github.users.repository.UserRepository;
import com.github.users.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;


    @Test
    void testeBuscaUsuariosApi(){

        User[] usuarioFalsos = {
                new User("user1", "url1"),
                new User("user2", "url2")
        };

        when(restTemplate.getForObject("https://api.github.com/users", User[].class))
                .thenReturn(usuarioFalsos);

        List<User> result = userService.getUsersGit();

        assertEquals(2, result.size());
        assertEquals("user1", result.get(0).getLogin());
    }


    @Test
    void testCreateUsers() {

        User[] usuarioFalsos = {
                new User("jose", "https://api.github.com/users/jose"),
                new User("eloa", "https://api.github.com/users/eloa")
        };

        when(restTemplate.getForObject("https://api.github.com/users", User[].class))
                .thenReturn(usuarioFalsos);

        when(userRepository.saveAll(anyList())).thenAnswer(invocation -> invocation.getArgument(0));

        List<User> result = userService.createUsers();

        ArgumentCaptor<List<User>> captor = ArgumentCaptor.forClass(List.class);
        verify(userRepository, times(1)).saveAll(captor.capture());

        List<User> listaEnviada = captor.getValue();

        assertEquals(2, listaEnviada.size());
        assertEquals("jose", listaEnviada.get(0).getLogin());
        assertEquals("eloa", listaEnviada.get(1).getLogin());

        assertEquals(2, result.size());
        assertEquals("jose", result.get(0).getLogin());
        assertEquals("eloa", result.get(1).getLogin());
    }

}
