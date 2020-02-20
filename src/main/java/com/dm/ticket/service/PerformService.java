package com.dm.ticket.service;

import com.dm.ticket.model.PageData;
import com.dm.ticket.model.dto.PerformDetailDto;
import com.dm.ticket.model.dto.PerformDto;
import com.dm.ticket.model.entity.Perform;

import java.util.List;

public interface PerformService {

    boolean addPerform(PerformDto dto);

    boolean deletePerform(Long id);

    boolean modify(Perform perform);

    Perform getPerformById(Long id);

    PageData<List<PerformDetailDto>> getAllPerformByPage(Long pageNum);
}
