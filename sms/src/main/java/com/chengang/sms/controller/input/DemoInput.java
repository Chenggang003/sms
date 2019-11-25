package com.chengang.sms.controller.input;

import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

/**
 * @author: chengang
 * @date: 2019/11/25
 * @description:
 */
@Data
public class DemoInput {

    @NotEmpty(message = "name不能为空")
    private String name;

    @NotEmpty(message = "area不能为空")
    private String area;
}
