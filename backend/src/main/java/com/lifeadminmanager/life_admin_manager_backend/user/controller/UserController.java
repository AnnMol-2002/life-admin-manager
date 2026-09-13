package com.lifeadminmanager.life_admin_manager_backend.user.controller;

import com.lifeadminmanager.life_admin_manager_backend.user.dto.CreateUserRequest;
import com.lifeadminmanager.life_admin_manager_backend.user.dto.UserResponse;
import com.lifeadminmanager.life_admin_manager_backend.user.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService){
        this.userService=userService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponse createUser(@Valid @RequestBody CreateUserRequest request){
        return userService.createUser(request);
    }

}
