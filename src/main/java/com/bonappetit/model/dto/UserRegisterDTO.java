package com.bonappetit.model.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class UserRegisterDTO {

    @NotNull
    @Size(min = 3, max = 20, message = "Username length must be between 3 and 20 characters (inclusive of 3 and 20)!")
    private String username;

    @NotNull
    @Email(message = "Must contain '@'!")
    private String email;

    @NotNull
    @Size(min = 2, max = 20, message = "Password length must be between 3 and 20 characters (inclusive of 3 and 20)!")
    private String password;

    private String confirmPassword;

    public UserRegisterDTO() {
    }

    public @NotNull @Size(min = 3, max = 20, message = "Username length must be between 3 and 20 characters (inclusive of 3 and 20)!") String getUsername() {
        return username;
    }

    public void setUsername(@NotNull @Size(min = 3, max = 20, message = "Username length must be between 3 and 20 characters (inclusive of 3 and 20)!") String username) {
        this.username = username;
    }

    public @NotNull @Email(message = "Must contain '@'!") String getEmail() {
        return email;
    }

    public void setEmail(@NotNull @Email(message = "Must contain '@'!") String email) {
        this.email = email;
    }

    public @NotNull @Size(min = 2, max = 20, message = "Password length must be between 3 and 20 characters (inclusive of 3 and 20)!") String getPassword() {
        return password;
    }

    public void setPassword(@NotNull @Size(min = 2, max = 20, message = "Password length must be between 3 and 20 characters (inclusive of 3 and 20)!") String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}
