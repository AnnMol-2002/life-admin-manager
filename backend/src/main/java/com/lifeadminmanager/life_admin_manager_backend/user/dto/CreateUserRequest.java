package com.lifeadminmanager.life_admin_manager_backend.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateUserRequest(
        @NotBlank(message = "Email is required")
        @Email(message = "Invalid email format")
        String email,

        @NotBlank(message = "Password is required")
        @Size(min = 8,message = "Password must be atleast 8 characters")
        String password,

        @NotBlank(message = "First Name is Required")
        String firstName,

        @NotBlank(message = "Last Name is Required")
        String lastName
) {
}
