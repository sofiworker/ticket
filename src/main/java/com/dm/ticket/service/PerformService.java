package com.dm.ticket.service;

import com.dm.ticket.model.PageData;
import com.dm.ticket.model.dto.PerformDetailDto;
import com.dm.ticket.model.dto.PerformDto;
import com.dm.ticket.model.dto.PerformOverview;
import com.dm.ticket.model.dto.PerformThumbnail;
import com.dm.ticket.model.entity.City;
import com.dm.ticket.model.entity.Perform;

import java.util.List;
import java.util.Map;

public interface PerformService {

    Map<String, Long> addPerform(PerformDto dto);

    boolean deletePerform(Long id);

    boolean modify(Perform perform);

    PerformDetailDto getPerformById(Long id);

    PageData<List<PerformThumbnail>> getAllPerformByPage(Long pageNum, Integer orderType, Map<String, Long> conditions);

    PageData<List<PerformOverview>> getPerform(Long pageNum, Integer orderType, Map<String, Long> conditions);

    List<PerformThumbnail> getRecommendList(Integer categoryId);

    PageData<List<PerformThumbnail>> searchPerform(Long pageNum, String str, Integer orderType, Map<String, Object> conditions);

    Map<Long, City> getCitiesByName(String name);

    boolean addViewPages(Long performId);
}
