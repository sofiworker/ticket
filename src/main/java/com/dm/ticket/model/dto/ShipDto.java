package com.dm.ticket.model.dto;


import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class ShipDto {
    private Long id;
    @NotBlank
    private String name;
    @NotBlank
    private String phone;
    @NotBlank
    private String location;
}
