package com.dm.ticket.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.Query;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dm.ticket.mapper.TicketMapper;
import com.dm.ticket.model.dto.TicketDto;
import com.dm.ticket.model.entity.Ticket;
import com.dm.ticket.service.TicketService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class TicketServiceImpl implements TicketService {

    private TicketMapper mapper;

    @Autowired
    public void setMapper(TicketMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public boolean addNewTicket(List<TicketDto> dto) {
        int length = 0;
        for (TicketDto ticketDto : dto) {
            Ticket ticket = new Ticket();
            BeanUtils.copyProperties(ticketDto, ticket);
            length += mapper.insert(ticket);
        }
        return length == dto.size();
    }

    @Override
    public List<Ticket> getTicketDetail(Long performId) {
        QueryWrapper<Ticket> query = new QueryWrapper<>();
        query.eq("perform_id", performId);
        return mapper.selectList(query);
    }

    @Override
    public boolean deleteTickets(Long performId) {
        QueryWrapper<Ticket> wrapper = new QueryWrapper<>();
        wrapper.eq("perform_id", performId);
        int delete = mapper.delete(wrapper);
        return delete > 0;
    }
}
