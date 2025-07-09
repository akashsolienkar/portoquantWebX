package com.quant.portoquant.application.service.impl;




import com.quant.portoquant.api.dto.UserRequest;
import com.quant.portoquant.api.dto.UserResponse;
import com.quant.portoquant.api.mapper.UserMapper;
import com.quant.portoquant.application.service.UserService;
import com.quant.portoquant.domain.model.User;
import com.quant.portoquant.infrastructure.repository.UserRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    
    @Override
    public UserResponse register(UserRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("Email already in use.");
        }
        String encodedPassword = passwordEncoder.encode(request.getPassword());
        User user = UserMapper.toEntity(request);
        user.setPassword(encodedPassword);
        return UserMapper.toResponse(userRepository.save(user));
    }

    @Override
    public UserResponse getById(UUID id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        return UserMapper.toResponse(user);
    }

    @Override
    public UserResponse update(UUID id, UserRequest request) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());
        user.setRole(request.getRole());
        return UserMapper.toResponse(userRepository.save(user));
    }

    @Override
    public void delete(UUID id) {
        if (!userRepository.existsById(id)) {
            throw new EntityNotFoundException("User not found");
        }
        userRepository.deleteById(id);
    }
	
}
