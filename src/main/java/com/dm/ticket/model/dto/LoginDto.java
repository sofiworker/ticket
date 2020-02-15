package com.dm.ticket.model.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class LoginDto {
    @NotBlank
    private String account;
    @Size(max = 20, min = 6)
    private String password;
}
