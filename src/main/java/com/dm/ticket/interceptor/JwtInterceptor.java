package com.dm.ticket.interceptor;

import com.dm.ticket.exception.TokenException;
import com.dm.ticket.util.JwtUtil;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class JwtInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("token");
        if (token == null || "".equals(token)) {
            throw new TokenException("请求头中token字段不存在/token为空");
        }
        JwtUtil.verifyToken(token);
        return true;
    }
}
