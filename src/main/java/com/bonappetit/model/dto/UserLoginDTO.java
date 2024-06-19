package com.bonappetit.model.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class UserLoginDTO {
//
    @NotBlank
    @Size(min = 3, max = 20, message = "Username length must be between 3 and 20 characters (inclusive of 3 and 20)!")
    private String username;

    private String password;

    public UserLoginDTO() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
