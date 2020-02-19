package com.dm.ticket.model.dto;


import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class CategoryDto {

    @NotBlank
    private String name;
    private String pic;
}
