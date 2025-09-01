package com.github.users.model.dto;

import java.util.Set;

public class UserResponse {

    private Long id;
    private String login;
    private String url;
    private Set<String> roles;

    public UserResponse() {}

    public UserResponse(Long id, String login, String url, Set<String> roles) {
        this.id = id;
        this.login = login;
        this.url = url;
        this.roles = roles;
    }

    public Long getId() { return id; }
    public String getLogin() { return login; }
    public String getUrl() { return url; }
    public Set<String> getRoles() { return roles; }
}
