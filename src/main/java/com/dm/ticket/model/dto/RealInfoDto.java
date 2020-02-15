package com.dm.ticket.model.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class RealInfoDto {

    private Long id;
    @NotBlank
    private String actualName;
    @Size(max = 18, min = 18, message = "请输入正确长度的身份证号")
    private String idNumber;
}
