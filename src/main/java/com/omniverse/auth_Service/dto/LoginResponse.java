package com.omniverse.auth_Service.dto;

public class LoginResponse {

    private String message;
    private String username;

    // later we will add JWT here

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
