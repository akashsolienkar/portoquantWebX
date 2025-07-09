package com.quant.portoquant.infrastructure.security.auth;

import com.quant.portoquant.api.dto.UserLoginRequest;
import com.quant.portoquant.api.dto.UserLoginResponse;

public interface AuthService {
 UserLoginResponse login(UserLoginRequest request);
}
