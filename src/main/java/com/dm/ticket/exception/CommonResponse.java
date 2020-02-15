package com.dm.ticket.exception;

import com.dm.ticket.model.CommonResponseData;
import com.dm.ticket.model.StrResponseData;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

@ControllerAdvice(annotations = {RestController.class})
public class CommonResponse implements ResponseBodyAdvice<Object> {

    @Override
    public boolean supports(MethodParameter returnType, Class converterType) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType,
                                  MediaType selectedContentType, Class selectedConverterType,
                                  ServerHttpRequest request, ServerHttpResponse response) {
        Object o;
        if (body instanceof CommonResponseData) {
            o = body;
        } else if (body instanceof StrResponseData){
            o = body;
        }else {
            CommonResponseData data = new CommonResponseData();
            data.setData(body);
            o = data;
        }
        return o;
    }
}
