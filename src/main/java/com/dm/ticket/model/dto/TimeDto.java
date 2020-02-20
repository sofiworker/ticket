package com.dm.ticket.model.dto;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class TimeDto {

    private Long performId;

    private Timestamp time;
}
