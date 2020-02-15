package com.dm.ticket.model;

import lombok.Data;

@Data
public class CommonResponseData {
    private int code = 200;
    private String msg = "成功";
    private Object data;
}
