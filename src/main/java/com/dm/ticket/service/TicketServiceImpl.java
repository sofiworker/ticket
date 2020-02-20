package com.dm.ticket.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dm.ticket.mapper.TicketMapper;
import com.dm.ticket.model.dto.TicketDto;
import com.dm.ticket.model.entity.Ticket;
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
    public boolean addNewTicket(TicketDto dto) {
        Ticket ticket = new Ticket();
        BeanUtils.copyProperties(dto, ticket);
        return mapper.insert(ticket) == 1;
    }

    @Override
    public List<Ticket> getTicketDetail(Long timeId) {
        QueryWrapper<Ticket> query = new QueryWrapper<>();
        query.eq("time_id", timeId);
        return mapper.selectList(query);
    }
}
