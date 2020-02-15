package com.dm.ticket.model.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
public class EmailDto {

    private Long id;
    @Email(message = "请输入正确格式的邮箱")
    private String email;
}
