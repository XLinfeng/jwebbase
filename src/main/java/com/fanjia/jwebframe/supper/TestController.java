package com.fanjia.jwebframe.supper;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class TestController {

    @ResponseBody
    @GetMapping(value = "/test/{pwd}")
    public String getPwd(@PathVariable("pwd") String pwd){
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String pwdStr = bCryptPasswordEncoder.encode(pwd);
        return pwdStr;
    }
}
