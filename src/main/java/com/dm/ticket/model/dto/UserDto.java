package com.dm.ticket.model.dto;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class UserDto {

    private Long id;

    private String nickName;

    private String actualName;

    private String avatar;

    private Boolean sex;

    private Timestamp birthday;

    private String idNumber;

    private String phoneNumber;

    private String email;

    private String token;
}
