package com.github.users.controller;

import com.github.users.model.dto.UserResponse;
import com.github.users.model.entity.User;
import com.github.users.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService){
        this.userService=userService;
    }

    @PostMapping("/create")
    public ResponseEntity<List<User>> createUsers(){
        List<User> lista = userService.createUsers();
        if(lista.isEmpty()){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(lista);
    }

    @GetMapping("/read")
    public ResponseEntity<List<UserResponse>> getAllUsersWithRoles(){

        List<UserResponse> list = userService.getAllUsersWithRoles();

        return ResponseEntity.ok(list) ;
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getId(@PathVariable Long id){
        UserResponse userResponse = userService.getId(id);

        return ResponseEntity.ok(userResponse);

    }

}
