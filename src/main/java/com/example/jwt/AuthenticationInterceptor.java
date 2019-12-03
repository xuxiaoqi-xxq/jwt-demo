package com.example.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.exceptions.JWTDecodeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

public class AuthenticationInterceptor implements HandlerInterceptor {

    @Override
    public  boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        String token = request.getHeader("token");
    System.out.println(token);
        if(! (handler instanceof HandlerMethod)){
            return true;
        }

        HandlerMethod handlerMethod = (HandlerMethod)handler;
        Method method = handlerMethod.getMethod();
        if(method.isAnnotationPresent(Auth.class)){
            Auth auth = method.getAnnotation(Auth.class);
            if(!auth.required()){
                return true;
            }else{
                if(token == null){
                    throw new RuntimeException("未授权请求！");
                }
                String uid;
                try{
                    uid = JWT.decode(token).getClaim("uid").toString();
                }catch (JWTDecodeException e) {
                    throw new RuntimeException("解析Token异常");
                }
                boolean isVerify = JwtUtil.isVerify(token,"123456");
                if(!isVerify){
                    throw new RuntimeException("非法访问!");
                }
                return true;
            }
        }
        return true;
    }
}
