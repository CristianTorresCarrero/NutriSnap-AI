package com.nutrisnap.service;

import com.nutrisnap.dto.LoginRequest;
import com.nutrisnap.dto.LoginResponse;

public interface AuthService {

    LoginResponse login(LoginRequest request);

}