package com.dm.ticket.handler;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.dm.ticket.model.StrResponseData;
import com.dm.ticket.exception.TokenException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private StrResponseData data = new StrResponseData();

    @ExceptionHandler(TokenException.class)
    public StrResponseData tokenException(TokenException e){
        data.setCode(300);
        data.setMsg(e.getMessage());
        return data;
    }

    @ExceptionHandler(JWTVerificationException.class)
    public StrResponseData jwtValidateException(){
        data.setCode(300);
        data.setMsg("token失效/验证失败");
        return data;
    }
}
