package com.dm.ticket.model.dto;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class UserInfoUpdateDto {
    private Long uid;
    private String nickName;
    private Boolean sex;
    private Timestamp birthday;
}
