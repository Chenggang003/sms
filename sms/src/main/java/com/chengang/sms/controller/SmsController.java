package com.chengang.sms.controller;

import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author: chengang
 * @date: 2019/11/25
 * @description:
 */
@RestController
@RequestMapping("/sms")
public class SmsController {

    @Autowired
    private Environment environment;

    // 设置鉴权参数，初始化客户端
    // 地域ID
    // 您的AccessKey ID
    // 您的AccessKey Secret
    private DefaultProfile profile = DefaultProfile.getProfile("cn-shanghai","LTAI4FhLC7Bdfi7xqwviyhEU","EdqCZ3cmDWDGkWa5Bd6PLFDmzVd5hD");

    private IAcsClient client = new DefaultAcsClient(profile);

    private static void log_print(String functionName, Object result) {
        Gson gson = new Gson();
        System.out.println("-------------------------------" + functionName + "-------------------------------");
        System.out.println(gson.toJson(result));
    }

    /**
     * 发送短信
     */
    @RequestMapping("/send")
    private String sendSms() throws ClientException {

        CommonRequest request = new CommonRequest();
        request.setSysDomain("dysmsapi.aliyuncs.com");
        request.setSysVersion("2017-05-25");
        request.setSysAction("SendSms");
        // 接收短信的手机号码
        request.putQueryParameter("PhoneNumbers", "18621285725");
        // 短信签名名称。请在控制台签名管理页面签名名称一列查看（必须是已添加、并通过审核的短信签名）。
        request.putQueryParameter("SignName", "sms-service");
        // 短信模板ID
        request.putQueryParameter("TemplateCode", "SMS_178451864");
        // 短信模板变量对应的实际值，JSON格式。
        request.putQueryParameter("TemplateParam", "{\"code\":\"8888\"}");
        CommonResponse commonResponse = client.getCommonResponse(request);
        String data = commonResponse.getData();
        String sData = data.replaceAll("'\'", "");
        log_print("sendSms", sData);
        Gson gson = new Gson();
        Map map = gson.fromJson(sData, Map.class);
        Object bizId = map.get("BizId");
        return bizId.toString();
    }
}
