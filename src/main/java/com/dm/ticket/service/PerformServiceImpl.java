package com.dm.ticket.service;

import com.dm.ticket.mapper.PerformMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PerformServiceImpl implements PerformService {

    private PerformMapper performMapper;

    @Autowired
    public void setPerformMapper(PerformMapper performMapper) {
        this.performMapper = performMapper;
    }

    @Override
    public boolean addPerform() {
        return false;
    }
}
