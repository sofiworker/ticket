package com.dm.ticket.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dm.ticket.model.entity.Ticket;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface TicketMapper extends BaseMapper<Ticket> {
}
