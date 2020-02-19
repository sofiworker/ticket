package com.dm.ticket.service;

import com.dm.ticket.mapper.PerformMapper;
import com.dm.ticket.model.dto.PerformDto;
import com.dm.ticket.model.entity.Perform;
import org.springframework.beans.BeanUtils;
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
    public boolean addPerform(PerformDto dto) {
        Perform perform = new Perform();
        BeanUtils.copyProperties(dto, perform);
        return performMapper.insert(perform) == 1;
    }
}
