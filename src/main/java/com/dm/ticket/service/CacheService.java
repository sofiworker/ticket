package com.dm.ticket.service;


import com.dm.ticket.model.entity.Ticket;

import java.math.BigDecimal;
import java.util.List;

public interface CacheService {

    List<Ticket> getTicketInventory(Long performId);

    List<Ticket> updateTicketCache(List<Ticket> tickets, Long performId);
}
