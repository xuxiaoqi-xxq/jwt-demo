package com.example.jwt;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/")
public class UserController {

    @PostMapping("/login/{username}/{password}")
    @Auth(required = false)
    public Object login(@PathVariable("username")String username,@PathVariable("password")String password){
//        String username = jsonObject.getString("username");
//        String password = jsonObject.getString("password");
        if(username.equals("test") && password.equals("123456")){
            String token = JwtUtil.createJWT(3600000);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("token",token);
            return jsonObject;
        }else{
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("token",null);
            jsonObject.put("error","用户名或密码错误");
            return jsonObject;
        }
    }

    @Auth
    @GetMapping("/msg")
    public String get(){
        return "ok";
    }

    @Auth(required = false)
    @GetMapping("/test")
    public String test(){
        return new Date().toLocaleString();
    }
}
