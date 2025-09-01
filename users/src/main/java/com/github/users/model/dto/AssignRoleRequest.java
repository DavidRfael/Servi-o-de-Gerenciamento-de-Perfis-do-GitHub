package com.github.users.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;

public class AssignRoleRequest {
    @NotNull(message = "userId não pode ser nulo")
    @JsonProperty("user_id")
    private Long user_Id;

    @NotNull(message = "roleId não pode ser nulo")
    @JsonProperty("role_id")
    private Long role_Id;


    public Long getUser_Id() {
        return user_Id;
    }

    public void setUser_Id(Long user_Id) {
        this.user_Id = user_Id;
    }

    public Long getRole_Id() {
        return role_Id;
    }

    public void setRole_Id(Long role_Id) {
        this.role_Id = role_Id;
    }
}
