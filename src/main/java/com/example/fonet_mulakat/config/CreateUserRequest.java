package com.example.fonet_mulakat.config;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateUserRequest {
    private String username;
    private String password;
    private String role;



    // Default constructor
    public CreateUserRequest() {
    }

    // Constructor with fields
    public CreateUserRequest(String username, String password,String role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }
}
