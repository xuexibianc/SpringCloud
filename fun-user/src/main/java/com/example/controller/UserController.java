package com.example.controller;

import com.example.dto.UserLoginRequest;
import com.example.dto.UserRegisterRequest;
import com.example.entity.User;
import com.example.service.UserService;
import jakarta.validation.Valid;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wangxinliang@civil.com.cn
 * @date 2025/07/19
 */
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * @author wangxinliang@civil.com.cn
     * @param userRegisterRequest
     * @return {@link ResponseEntity }<{@link User }>
     * @功能介绍  注册新账号
     */
    @PostMapping("/users/register")
    public ResponseEntity<User> register(@RequestBody @Valid UserRegisterRequest userRegisterRequest){

        Integer userId = userService.register(userRegisterRequest);

        User user = userService.getUserById(userId);

        return ResponseEntity.status(HttpStatus.CREATED).body(user);

    }


    /**
     * @author wangxinliang@civil.com.cn
     * @param userLoginRequest
     * @return {@link ResponseEntity }<{@link User }>
     * @功能介绍  登陆
     */
    @PostMapping("users/login")
    public ResponseEntity<User> login(@RequestBody @Valid UserLoginRequest userLoginRequest){

        User user = userService.login(userLoginRequest);

        return ResponseEntity.status(HttpStatus.OK).body(user);

    }
}
