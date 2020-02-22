package com.dm.ticket.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.PageUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dm.ticket.mapper.*;
import com.dm.ticket.model.PageData;
import com.dm.ticket.model.dto.PerformDetailDto;
import com.dm.ticket.model.dto.PerformDto;
import com.dm.ticket.model.dto.PerformThumbnail;
import com.dm.ticket.model.entity.*;
import com.dm.ticket.service.PerformService;
import com.dm.ticket.util.SnowflakeUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PerformServiceImpl implements PerformService {

    private PerformMapper performMapper;
    private CategoryMapper categoryMapper;
    private SubclassMapper subclassMapper;
    private ArtistMapper artistMapper;
    private CityMapper cityMapper;
    private LocationMapper locationMapper;
    private TimeMapper timeMapper;
    private TicketMapper ticketMapper;

    @Autowired
    public void setPerformMapper(PerformMapper performMapper) {
        this.performMapper = performMapper;
    }

    @Autowired
    public void setCategoryMapper(CategoryMapper categoryMapper) {
        this.categoryMapper = categoryMapper;
    }

    @Autowired
    public void setSubclassMapper(SubclassMapper subclassMapper) {
        this.subclassMapper = subclassMapper;
    }

    @Autowired
    public void setArtistMapper(ArtistMapper artistMapper) {
        this.artistMapper = artistMapper;
    }

    @Autowired
    public void setCityMapper(CityMapper cityMapper) {
        this.cityMapper = cityMapper;
    }

    @Autowired
    public void setLocationMapper(LocationMapper locationMapper) {
        this.locationMapper = locationMapper;
    }

    @Autowired
    public void setTimeMapper(TimeMapper timeMapper) {
        this.timeMapper = timeMapper;
    }

    @Autowired
    public void setTicketMapper(TicketMapper ticketMapper) {
        this.ticketMapper = ticketMapper;
    }

    @Override
    public Map<String, Long> addPerform(PerformDto dto) {
        Perform perform = new Perform();
        BeanUtils.copyProperties(dto, perform);
        Long id = SnowflakeUtil.createId();
        perform.setId(id);
        boolean isSuccess = performMapper.insert(perform) == 1;
        Map<String, Long> result = new HashMap<>();
        if (isSuccess) {
            result.put("success", id);
        }else {
            result.put("fail", null);
        }
        return result;
    }

    @Override
    public boolean deletePerform(Long id) {
        return performMapper.deleteById(id) == 1;
    }

    @Override
    public boolean modify(Perform perform) {
        return performMapper.updateById(perform) == 1;
    }

    @Override
    public PerformDetailDto getPerformById(Long id) {
        Perform perform = performMapper.selectById(id);
        QueryWrapper<Time> timeQuery = new QueryWrapper<>();
        timeQuery.eq("perform_id", id).orderBy(true, true, "time");
        List<Time> times = timeMapper.selectList(timeQuery);
        QueryWrapper<Ticket> ticketQuery = new QueryWrapper<>();
        ticketQuery.eq("perform_id", id).orderBy(true, true, "money");
        List<Ticket> tickets = ticketMapper.selectList(ticketQuery);
        PerformDetailDto detailDto = new PerformDetailDto();
        detailDto.setId(perform.getId()).setCover(perform.getCover())
                .setTitle(perform.getTitle()).setContent(perform.getContent())
                .setTicketNotice(perform.getTicketNotice())
                .setViewNotice(perform.getViewNotice())
                .setCategory(categoryMapper.selectById(perform.getCategoryId()))
                .setSubclass(subclassMapper.selectById(perform.getSubclassId()))
                .setArtist(artistMapper.selectById(perform.getArtistId()))
                .setCity(cityMapper.selectById(perform.getCityId()))
                .setLocation(locationMapper.selectById(perform.getLocationId()))
                .setTimes(times)
                .setTickets(tickets);
        return detailDto;
    }

    @Override
    public PageData<List<PerformThumbnail>> getAllPerformByPage(Long pageNum) {
        Page<Perform> page = new Page<>(pageNum, 30);
        IPage<Perform> data = performMapper.getPerformData(page);
        PageData<List<PerformThumbnail>> pageData = new PageData<>();
        List<Perform> performList = data.getRecords();
        pageData.setData(combinedData(performList)).setCurPage(data.getCurrent()).setTotalPage(data.getTotal());
        return pageData;
    }

    private List<PerformThumbnail> combinedData(List<Perform> performs) {
        List<PerformThumbnail> list = new ArrayList<>();
        for (Perform perform : performs) {
            PerformThumbnail thumbnail = new PerformThumbnail();
            QueryWrapper<Time> timeQuery = new QueryWrapper<>();
            timeQuery.eq("perform_id", perform.getId()).orderBy(true, true, "time");
            Time time = timeMapper.selectList(timeQuery).get(0);
            QueryWrapper<Ticket> ticketQuery = new QueryWrapper<>();
            ticketQuery.eq("time_id", time.getId()).orderBy(true, true, "money");
            Ticket ticket = ticketMapper.selectList(ticketQuery).get(0);
            thumbnail.setId(perform.getId()).setCover(perform.getCover())
                    .setTitle(perform.getTitle())
                    .setArtist(artistMapper.selectById(perform.getArtistId()))
                    .setCity(cityMapper.selectById(perform.getCityId()))
                    .setLocation(locationMapper.selectById(perform.getLocationId()))
                    .setTime(time.getTime())
                    .setMoney(ticket.getMoney())
                    .setCreateTime(perform.getCreateTime());
        }
        return list;
    }

    @Override
    public List<PerformThumbnail> getRecommendList(Integer categoryId) {
        QueryWrapper<Perform> query = new QueryWrapper<>();
        query.eq("category_id", categoryId);
        List<Perform> performs = performMapper.selectList(query);
        List<PerformThumbnail> thumbnails = combinedData(performs);
        List<PerformThumbnail> list = CollUtil.sortByProperty(thumbnails, "time");
        List<PerformThumbnail> result = new ArrayList<>();
        for (int i=0; i<7; i++) {
            result.add(list.get(i));
        }
        return result;
    }

    @Override
    public PageData<List<PerformThumbnail>> searchPerform(Long pageNum, String str) {
        QueryWrapper<Artist> artistQuery = new QueryWrapper<>();
        artistQuery.like("name", str);
        List<Artist> artists = artistMapper.selectList(artistQuery);
        Page<Perform> page = new Page<>(pageNum, 30);
        IPage<Perform> iPage = performMapper.searchPerform(page, str, artists);
        List<Perform> performList = iPage.getRecords();
        PageData<List<PerformThumbnail>> pageData = new PageData<>();
        return pageData.setCurPage(iPage.getCurrent()).setData(combinedData(performList)).setTotalPage(iPage.getTotal());
    }

    @Override
    public Map<Long, City> getCitiesByName(String name) {
        QueryWrapper<Perform> wrapper = new QueryWrapper<>();
        wrapper.eq("title", name);
        List<Perform> performs = performMapper.selectList(wrapper);
        Map<Long, City> map = new LinkedHashMap<>();
        for (Perform perform : performs) {
            map.put(perform.getId(), cityMapper.selectById(perform.getCityId()));
        }
        return map;
    }
}
