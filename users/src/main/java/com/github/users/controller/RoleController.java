package com.github.users.controller;

import com.github.users.model.dto.AssignRoleRequest;
import com.github.users.model.dto.RoleRequest;
import com.github.users.model.dto.RoleResponse;
import com.github.users.model.dto.UserResponse;
import com.github.users.model.entity.Role;
import com.github.users.service.RoleService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/role")
public class RoleController {

    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @PostMapping("/create")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<RoleResponse> create(@RequestBody @Valid RoleRequest roleRequest){
        try {
            RoleResponse roleResponse = roleService.create(roleRequest);
            return ResponseEntity.status(HttpStatus.CREATED).body(roleResponse);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new RoleResponse(null, "Erro: " + e.getMessage(),null));
        }

    }

    @PostMapping("/assign")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserResponse> assignRole(@RequestBody @Valid AssignRoleRequest assignRoleRequest){
        UserResponse userResponse = roleService.assignRoleToUser(assignRoleRequest);

        return ResponseEntity.ok(userResponse);

    }

    @GetMapping("/read")
    public ResponseEntity<List<RoleResponse>> getAllRoles(){
        List<RoleResponse> lista = roleService.getAllRole();
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoleResponse> getId(@PathVariable Long id){
        RoleResponse role = roleService.getId(id);
        return ResponseEntity.ok(role);
    }

}
