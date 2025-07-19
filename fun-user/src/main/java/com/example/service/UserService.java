package com.example.service;

import com.example.dto.UserLoginRequest;
import com.example.dto.UserRegisterRequest;
import com.example.entity.User;

public interface UserService {

    User getUserById(Integer userId);

    User login(UserLoginRequest userLoginRequest);

    Integer register(UserRegisterRequest userRegisterRequest);
}
