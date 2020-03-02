package com.dm.ticket.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dm.ticket.mapper.TicketMapper;
import com.dm.ticket.model.entity.Ticket;
import com.dm.ticket.service.CacheService;
import org.ehcache.core.EhcacheManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;


@Service
public class CacheServiceImpl implements CacheService {

    private TicketMapper ticketMapper;
    private CacheManager cacheManager;

    @Autowired
    public void setTicketMapper(TicketMapper ticketMapper) {
        this.ticketMapper = ticketMapper;
    }

    @Autowired
    public void setCacheManager(CacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }

    @Override
//    @Cacheable(value = "ticketCache", key = "#performId")
    public List<Ticket> getTicketInventory(Long performId) {
        QueryWrapper<Ticket> wrapper = new QueryWrapper<>();
        wrapper.eq("perform_id", performId);
        System.out.println("=======>调用数据库了");
        return ticketMapper.selectList(wrapper);
    }

    @Override
//    @CachePut(value = "ticketCache", key = "#performId")
    public List<Ticket> updateTicketCache(List<Ticket> tickets, Long performId) {
        return tickets;
    }
}
