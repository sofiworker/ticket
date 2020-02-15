package com.dm.ticket.handler;

import com.dm.ticket.model.StrResponseData;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.List;

/**
 * @description 验证处理
 */
@RestControllerAdvice
public class ValidExceptionHandler {

    private StrResponseData data = new StrResponseData();
    private HashMap<String, String> map = new HashMap<>(16);

    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    public StrResponseData valid(MethodArgumentNotValidException ex){
        BindingResult result = ex.getBindingResult();
        if (result.hasErrors()) {
            List<ObjectError> errors = result.getAllErrors();
            for (ObjectError error : errors) {
                FieldError fieldError = (FieldError) error;
                map.put(fieldError.getField(), fieldError.getDefaultMessage());
            }
        }
        data.setCode(300);
        String str = map.toString();
        String res = str.replace("{", "").replace("}", "").replace("=", ":");
        data.setMsg(res);
        return data;
    }

//    @ExceptionHandler(value = {ConstraintViolationException.class})
//    public StrResponseData valid(ConstraintViolationException ex){
//        data.setCode(400);
//        data.setMsg(ex.getMessage());
//        return data;
//    }
}
