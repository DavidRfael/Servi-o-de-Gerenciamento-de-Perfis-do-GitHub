package com.github.users.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

//@JsonInclude(JsonInclude.Include.NON_NULL) //Aqui estou verificando se o message estiver null nao aparecer no json
public class RoleResponse {

    private Long id;
    private String name;
    private String message;

    public RoleResponse(Long id, String name, String message) {
        this.id = id;
        this.name = name;
        this.message=message;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
