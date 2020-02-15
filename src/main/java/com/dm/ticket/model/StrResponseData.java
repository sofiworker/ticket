package com.dm.ticket.model;

import lombok.Data;

@Data
public class StrResponseData {
    private int code = 200;
    private String msg;
}
