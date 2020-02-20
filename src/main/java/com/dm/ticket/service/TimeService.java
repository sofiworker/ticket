package com.dm.ticket.service;


import com.dm.ticket.model.dto.TimeDto;
import com.dm.ticket.model.entity.Time;

import java.util.List;

public interface TimeService {

    boolean addNewTime(List<TimeDto> timeList);

    List<Time> getTime(Long performId);
}
