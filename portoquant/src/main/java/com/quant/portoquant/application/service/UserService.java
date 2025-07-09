package com.quant.portoquant.application.service;

import com.quant.portoquant.api.dto.UserRequest;
import com.quant.portoquant.api.dto.UserResponse;
import java.util.UUID;

public interface UserService {

    UserResponse register(UserRequest request);
    
    UserResponse getById(UUID id);
    
    UserResponse update(UUID id, UserRequest request);

    void delete(UUID id);
}
