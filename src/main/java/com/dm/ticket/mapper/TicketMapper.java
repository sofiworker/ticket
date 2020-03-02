package com.dm.ticket.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dm.ticket.model.entity.Ticket;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface TicketMapper extends BaseMapper<Ticket> {

    @Update("update ticket set count = count + #{count} where id = #{ticketId}")
    void addTicketByOrderStatus(Integer count, Long ticketId);
}
