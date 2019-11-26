package com.chengang.sso.controller;

import com.chengang.sso.entity.UserEntity;
import com.chengang.sso.utils.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author: chengang
 * @date: 2019/11/26
 * @description:
 */
@RestController
public class UserController {

    @Autowired
    private RedisTemplate<String,Object> redisTemplate;

    @PostMapping(value = "/login",produces= MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String login(String username,String password) {
        String token = UUID.randomUUID().toString();
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(username);
        userEntity.setPassword(password);

        redisTemplate.opsForValue().set("SESSION:" + token, JsonUtil.objectToJson(userEntity) ,1800, TimeUnit.SECONDS);
        redisTemplate.boundHashOps("SESSION").put(token,userEntity);

        return "SUCCESS";
    }

    @PostMapping(value = "/get")
    public UserEntity getUserByToken(String token) {
        String userInfo = (String) redisTemplate.opsForValue().get("SESSION:" + token);
        UserEntity userEntity = (UserEntity) redisTemplate.boundHashOps("SESSION").get(token);
        return userEntity;
    }
}
