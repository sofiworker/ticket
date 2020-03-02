package com.dm.ticket.model.dto;


import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderDto {

    private Long uid;
    private Long shipId;
    private Long performId;
    private Long ticketId;
    private Integer count;
}
