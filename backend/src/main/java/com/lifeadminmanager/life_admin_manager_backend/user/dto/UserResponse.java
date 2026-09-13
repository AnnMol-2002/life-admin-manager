package com.lifeadminmanager.life_admin_manager_backend.user.dto;

public record UserResponse(
        Long id,
        String email,
        String firstName,
        String lastName
) {
}
