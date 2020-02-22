package com.dm.ticket.model.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class TicketDto {

    private Long performId;

    private BigDecimal money;

    private Integer count;

    private String description;
}
