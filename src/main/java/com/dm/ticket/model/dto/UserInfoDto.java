package com.dm.ticket.model.dto;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class UserInfoDto {
    private String nickName;
    private String actualName;
    private Boolean sex;
    private Timestamp birthday;
    private String idNumber;
}
