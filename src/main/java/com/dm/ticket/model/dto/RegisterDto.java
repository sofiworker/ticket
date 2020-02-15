package com.dm.ticket.model.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class RegisterDto {

    @NotBlank
    @Size(max = 11, min = 11, message = "手机号不为空且需为11位")
    private String phoneNumber;
    @NotBlank
    @Size(max = 20, min = 6, message = "密码需为6~20位")
    private String password;
}
