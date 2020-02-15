package com.dm.ticket.controller;

import com.dm.ticket.model.StrResponseData;

public class BaseController {

    protected StrResponseData strData = new StrResponseData();

    protected StrResponseData errorResponse(String msg){
        strData.setCode(300);
        strData.setMsg(msg);
        return strData;
    }

    protected StrResponseData successResponse(String msg){
        strData.setCode(200);
        strData.setMsg(msg);
        return strData;
    }
}
