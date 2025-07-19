package com.example.service.Impl;

import com.example.dto.UserLoginRequest;
import com.example.dto.UserRegisterRequest;
import com.example.entity.User;
import com.example.mapper.UserMapper;
import com.example.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;

@Service
public class UserServiceImpl implements UserService {

    private final static Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserMapper userMapper;

    @Override
    public User getUserById(Integer userId) {
        return userMapper.getUserById(userId);
    }

    @Override
    public User login(UserLoginRequest userLoginRequest) {

        User user = userMapper.getUserByEmail(userLoginRequest.getEmail());


        //检查 user 是否存在
        if (user == null) {
            log.warn("该 email {} 尚未注册", userLoginRequest.getEmail());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        //使用 MD5 生成密码的杂凑值
        String hashedPassword = DigestUtils.md5DigestAsHex(userLoginRequest.getPassword().getBytes());

        if (user.getPassword().equals(hashedPassword)) {
            return user;
        } else {
            log.warn("email {} 的密码不正确", userLoginRequest.getEmail());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public Integer register(UserRegisterRequest userRegisterRequest) {
        Date now = new Date();
        userRegisterRequest.setCreatedDate(now);
        userRegisterRequest.setLastModifiedDate(now);

        User user = userMapper.getUserByEmail(userRegisterRequest.getEmail());

        // 检查注册的email
        if (user != null) {
            log.warn("该 Email {} 已经被注册", userRegisterRequest.getEmail());
            throw  new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        //使用MD5 生成密码的杂凑值
        String hashedPassword = DigestUtils.md5DigestAsHex(userRegisterRequest.getPassword().getBytes());
        userRegisterRequest.setPassword(hashedPassword);

        //创建账号
        userMapper.createUser(userRegisterRequest);

        return userRegisterRequest.getUserId();
    }
}
