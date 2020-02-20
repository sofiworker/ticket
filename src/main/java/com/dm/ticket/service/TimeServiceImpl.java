package com.dm.ticket.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dm.ticket.mapper.TimeMapper;
import com.dm.ticket.model.dto.TimeDto;
import com.dm.ticket.model.entity.Time;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public List<Time> getTime(Long performId) {
        QueryWrapper<Time> query = new QueryWrapper<>();
        query.eq("perform_id", performId);
        return timeMapper.selectList(query);
    }
}
