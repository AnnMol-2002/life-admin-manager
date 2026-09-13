package com.lifeadminmanager.life_admin_manager_backend.user.service;

import com.lifeadminmanager.life_admin_manager_backend.user.dto.CreateUserRequest;
import com.lifeadminmanager.life_admin_manager_backend.user.dto.UserResponse;
import com.lifeadminmanager.life_admin_manager_backend.user.entity.User;
import com.lifeadminmanager.life_admin_manager_backend.user.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository,PasswordEncoder passwordEncoder){
        this.userRepository=userRepository;
        this.passwordEncoder=passwordEncoder;
    }

    public UserResponse createUser(CreateUserRequest request){

        if(userRepository.findByEmail(request.email()).isPresent()){
            throw new IllegalArgumentException("Email is already registered");
        }

        String passwordHash=passwordEncoder.encode(request.password());

        LocalDateTime now=LocalDateTime.now();

        User user=new User();
        user.setEmail(request.email());
        user.setPasswordHash(passwordHash);
        user.setFirstName(request.firstName());
        user.setLastName(request.lastName());
        user.setCreatedAt(now);
        user.setUpdatedAt(now);

        User savedUser=userRepository.save(user);

        return new UserResponse(
                savedUser.getId(),
                savedUser.getEmail(),
                savedUser.getFirstName(),
                savedUser.getLastName()
        );
    }

    public Optional<User> findByEmail(String email){
        return userRepository.findByEmail(email);
    }

    public User createUser(User user){
        return userRepository.save(user);
    }
}
