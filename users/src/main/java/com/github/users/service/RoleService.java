package com.github.users.service;

import com.github.users.model.dto.AssignRoleRequest;
import com.github.users.model.dto.RoleRequest;
import com.github.users.model.dto.RoleResponse;
import com.github.users.model.dto.UserResponse;
import com.github.users.model.entity.Role;
import com.github.users.model.entity.User;
import com.github.users.repository.RoleRepository;
import com.github.users.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.client.ResourceAccessException;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Service
public class RoleService {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;

    public RoleService(RoleRepository roleRepository, UserRepository userRepository) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
    }

    //Responsavel por criar perfil
    public RoleResponse create(RoleRequest roleRequest){
        Role role = new Role();
        role.setNome(roleRequest.getName());

        if(roleRepository.existsByName(roleRequest.getName())){
            throw new IllegalArgumentException("Role já existe!");
        }

        try {
            Role roleSave = roleRepository.save(role);
            return new RoleResponse(roleSave.getId(), roleSave.getNome(), "Role criada com sucesso!");
        } catch (Exception e) {

            throw new RuntimeException("Erro ao salvar a role: " + e.getMessage());
        }
    }

    //Buscar todos os perfis
    public List<RoleResponse> getAllRole(){
        List<Role> listRole = roleRepository.findAll();
        return listRole.stream()
                .map(role -> new RoleResponse(role.getId(),role.getNome(),"Consulta realizada com sucesso")).collect(Collectors.toList());

    }

    //Buscar perfil por id
    public RoleResponse getId(Long id){

        Role role = roleRepository.findById(id)
                .orElseThrow(()->new ResourceAccessException("Role com ID " + id + " não encontrado!"));

        return new RoleResponse(role.getId(), role.getNome(), "Consulta realizada com sucesso");
    }

    //Atribuir um perfil ao usuário
    public UserResponse assignRoleToUser(@RequestBody AssignRoleRequest assignRoleRequest){
        Long user_id = assignRoleRequest.getUser_Id();
        Long role_id = assignRoleRequest.getRole_Id();

        User user = userRepository.findById(user_id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        Role role = roleRepository.findById(role_id)
                .orElseThrow(()-> new RuntimeException("Perfil não encontrado!"));

        user.getRoles().add(role);
        userRepository.save(user);

        Set<String> roleNames = user.getRoles().stream()
                .map(Role::getNome)
                .collect(Collectors.toSet());

        return new UserResponse(user.getId(), user.getLogin(), user.getUrl(), roleNames);

    }


}
