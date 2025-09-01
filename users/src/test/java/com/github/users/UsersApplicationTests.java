package com.github.users;

import com.github.users.model.entity.User;
import com.github.users.repository.UserRepository;
import com.github.users.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestTemplate;


@SpringBootTest
class UsersApplicationTests {

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

	@Test
	void buscarUsuariosGit() {

	}

}
