package com.chengang.sms.controller;

import com.chengang.sms.controller.input.DemoInput;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.validation.Valid;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * @author: chengang
 * @date: 2019/11/25
 * @description:
 */
@RestController
@RequestMapping("/demo")
public class DemoController {

    @PostMapping("/post")
    public String demoPost(@Valid DemoInput demoInput, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return bindingResult.getFieldError().getDefaultMessage();
        }
        return demoInput.getName() + demoInput.getArea();
    }

    public static void main(String[] args) throws IOException {
        String param = String.format("name=%s&area=%s","zhangsan","shanghai");
        String s = httpRequestData("http://localhost:8080/demo/post", param);
        System.out.println(s);
    }

    /**
     * 通用POST方法
     * @param url 		请求URL
     * @param param 	请求参数，如：username=test&password=1
     * @return			response
     * @throws IOException
     */
    public static String httpRequestData(String url, String param) throws IOException {
        StringBuffer buffer = new StringBuffer();

        URL u = new URL(url);
        HttpURLConnection con = (HttpURLConnection) u.openConnection();
        con.setRequestMethod("POST");
        con.setDoOutput(true);
        con.setDoInput(true);
        con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

        OutputStreamWriter osw = new OutputStreamWriter(con.getOutputStream(), "UTF-8");
        osw.write(param);
        osw.flush();
        osw.close();

        BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));
        String temp;
        while ((temp = br.readLine()) != null) {
            buffer.append(temp);
            buffer.append("\n");
        }
        return buffer.toString();
    }
}
