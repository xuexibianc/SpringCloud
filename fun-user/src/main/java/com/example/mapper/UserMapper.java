package com.example.mapper;

import com.example.dto.UserRegisterRequest;
import com.example.entity.User;

public interface UserMapper {

    User getUserById(Integer userId);

    User getUserByEmail(String email);

    Integer createUser(UserRegisterRequest userRegisterRequest);



}
