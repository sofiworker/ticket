package com.dm.ticket.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dm.ticket.mapper.TimeMapper;
import com.dm.ticket.model.dto.TimeDto;
import com.dm.ticket.model.entity.Time;
import com.dm.ticket.service.TimeService;
import com.dm.ticket.util.SnowflakeUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TimeServiceImpl implements TimeService {

    private TimeMapper timeMapper;

    @Autowired
    public void setTimeMapper(TimeMapper timeMapper) {
        this.timeMapper = timeMapper;
    }

    @Override
    public boolean addNewTime(List<TimeDto> timeList) {
        int length = 0;
        for (TimeDto timeDto : timeList) {
            Time time = new Time();
            BeanUtils.copyProperties(timeDto, time);
            length += timeMapper.insert(time);
        }
        return length == timeList.size();
    }

    @Override
    @Cacheable(value = "default", keyGenerator = "cacheKeyGenerator")
    public List<Time> getTime(Long performId) {
        QueryWrapper<Time> query = new QueryWrapper<>();
        query.eq("perform_id", performId);
        return timeMapper.selectList(query);
    }

    @Override
    public boolean deleteTimes(Long performId) {
        QueryWrapper<Time> wrapper = new QueryWrapper<>();
        wrapper.eq("perform_id", performId);
        return timeMapper.delete(wrapper) > 0;
    }
}
