package com.chengang.sso.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @author: chengang
 * @date: 2019/11/26
 * @description:
 */
@Data
public class UserEntity implements Serializable {

    private String username;

    private String password;
}
