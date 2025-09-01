package com.github.users.service;

import com.github.users.model.dto.UserResponse;
import com.github.users.model.entity.Role;
import com.github.users.model.entity.User;
import com.github.users.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    public final RestTemplate restTemplate;
    private final UserRepository userRepository;

    public UserService(RestTemplate restTemplate, UserRepository userRepository) {
        this.restTemplate = restTemplate;
        this.userRepository = userRepository;
    }

    //Buscar usuarios do git
    public List<User> getUsersGit(){
        String url = "https://api.github.com/users"+ "?per_page=30";
        User[] users = restTemplate.getForObject(url, User[].class);

        List<User> userList = Arrays.stream(users)
                .limit(30)
                .map(u -> new User(u.getLogin(),u.getUrl()))
                .collect(Collectors.toList());

        return userList;
    }


    //Salvar os usuarios que vem do Git
    public List<User> createUsers(){
        List<User> list = getUsersGit();
        try {
            return userRepository.saveAll(list);
        }catch (Exception e){
            return List.of();
        }
    }


    //Buscar todos usuarios com Perfis
    public List<UserResponse>  getAllUsersWithRoles(){
        return userRepository.findAll()
                .stream()
                .map(user -> new UserResponse(
                        user.getId(),
                        user.getLogin(),
                        user.getUrl(),
                        user.getRoles().stream()
                                .map(Role::getNome).collect(Collectors.toSet()))
                ).toList();

    }

    //Buscar usuario por id
    public UserResponse getId(Long id){
        User user = userRepository.findById(id)
                .orElseThrow(()-> new ResourceAccessException("Usuário com ID " + id + " não encontrado!"));

        return new UserResponse(
                user.getId(),
                user.getLogin(),
                user.getUrl(),
                user.getRoles().stream().map(role -> role.getNome()).collect(Collectors.toSet()));
    }


}
