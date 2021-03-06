package com.dm.ticket.service;


import com.dm.ticket.model.dto.TicketDto;
import com.dm.ticket.model.entity.Ticket;

import java.util.List;

public interface TicketService {

    boolean addNewTicket(List<TicketDto> dto);

    List<Ticket> getTicketDetail(Long timeId);

    boolean deleteTickets(Long performId);
}
