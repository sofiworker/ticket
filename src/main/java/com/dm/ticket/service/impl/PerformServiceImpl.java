package com.dm.ticket.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dm.ticket.mapper.*;
import com.dm.ticket.model.PageData;
import com.dm.ticket.model.dto.PerformDetailDto;
import com.dm.ticket.model.dto.PerformDto;
import com.dm.ticket.model.dto.PerformOverview;
import com.dm.ticket.model.dto.PerformThumbnail;
import com.dm.ticket.model.entity.*;
import com.dm.ticket.service.PerformService;
import com.dm.ticket.util.SnowflakeUtil;
import com.dm.ticket.util.TimeUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional(rollbackFor = Exception.class)
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
    @Cacheable(value = "default", keyGenerator = "cacheKeyGenerator")
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
    @Cacheable(value = "default", keyGenerator = "cacheKeyGenerator")
    public PageData<List<PerformThumbnail>> getAllPerformByPage(Long pageNum, Integer orderType,
                                                                Map<String, Long> conditions) {
        Page<Perform> page = new Page<>(pageNum, 30);
        List<Time> times;
        if (conditions.containsKey("time")) {
            QueryWrapper<Time> query = new QueryWrapper<>();
            switch (conditions.get("time").intValue()) {
                //今天
                case 0:
                    query.between("time", TimeUtil.getDailyStartTime(0), TimeUtil.getDailyEndTime(0));
                    break;
                    //明天
                case 1:
                    query.between("time", TimeUtil.getDailyStartTime(1), TimeUtil.getDailyEndTime(1));
                    break;
                    //本周末
                case 2:
                    query.between("time", TimeUtil.getWeekStartTime(), TimeUtil.getWeekEndTime());
                    break;
                    //一个月内
                case 3:
                    query.between("time", TimeUtil.getMonthStartTime(), TimeUtil.getMonthEndTime());
                    break;
                default:
                    break;
            }
            conditions.remove("time");
            times = timeMapper.selectList(query);
            return selectThumbnail(page, orderType, conditions, times);
        }else {
            IPage<Perform> data = performMapper.selectThumbnail(page, orderType, conditions);
            return combineThumbnailData(data);
        }
    }

    private PageData<List<PerformThumbnail>> selectThumbnail(Page<Perform> page, Integer orderType, Map<String, Long> conditions, List<Time> times){
        if (times.isEmpty()) {
            return new PageData<List<PerformThumbnail>>().setData(new ArrayList<>());
        }else {
            IPage<Perform> data = performMapper.getPerformData(page, orderType, conditions, times);
            return combineThumbnailData(data);
        }
    }

    private PageData<List<PerformThumbnail>> combineThumbnailData(IPage<Perform> data){
        PageData<List<PerformThumbnail>> pageData = new PageData<>();
        List<Perform> performList = data.getRecords();
        List<PerformThumbnail> thumbnails = combinedData(performList);
        pageData.setData(thumbnails)
                .setCurPage(data.getCurrent())
                .setTotalPage(data.getPages()).setTotal(data.getTotal());
        return pageData;
    }

    @Override
    @Cacheable(value = "default", keyGenerator = "cacheKeyGenerator")
    public PageData<List<PerformOverview>> getPerform(Long pageNum, Integer orderType,
                                                      Map<String, Long> conditions) {
        Page<Perform> page = new Page<>(pageNum, 30);
        IPage<Perform> data = performMapper.getOverViewPerform(page, orderType, conditions);
        PageData<List<PerformOverview>> pageData = new PageData<>();
        pageData.setData(getOverViewList(data.getRecords()))
                .setCurPage(data.getCurrent())
                .setTotalPage(data.getPages()).setTotal(data.getTotal());
        return pageData;
    }

    private List<PerformOverview> getOverViewList(List<Perform> performs) {
        List<PerformOverview> list = new ArrayList<>();
        for (Perform perform : performs) {
            PerformOverview overview = new PerformOverview();
            BeanUtils.copyProperties(perform, overview);
            overview.setCategory(categoryMapper.selectById(perform.getCategoryId()))
                    .setSubclass(subclassMapper.selectById(perform.getSubclassId()))
                    .setArtist(artistMapper.selectById(perform.getArtistId()))
                    .setCity(cityMapper.selectById(perform.getCityId()))
                    .setLocation(locationMapper.selectById(perform.getLocationId()));
            list.add(overview);
        }
        return list;
    }

    private List<PerformThumbnail> combinedData(List<Perform> performs) {
        List<PerformThumbnail> list = new ArrayList<>();
        for (Perform perform : performs) {
            PerformThumbnail thumbnail = new PerformThumbnail();
            QueryWrapper<Time> timeQuery = new QueryWrapper<>();
            timeQuery.eq("perform_id", perform.getId()).orderBy(true, true, "time");
            Time time = timeMapper.selectList(timeQuery).get(0);
            QueryWrapper<Ticket> ticketQuery = new QueryWrapper<>();
            ticketQuery.eq("perform_id", perform.getId()).orderBy(true, true, "money");
            Ticket ticket = ticketMapper.selectList(ticketQuery).get(0);
            thumbnail.setId(perform.getId()).setCover(perform.getCover())
                    .setTitle(perform.getTitle())
                    .setArtist(artistMapper.selectById(perform.getArtistId()))
                    .setCity(cityMapper.selectById(perform.getCityId()))
                    .setLocation(locationMapper.selectById(perform.getLocationId()))
                    .setCategory(categoryMapper.selectById(perform.getCategoryId()))
                    .setTime(time.getTime())
                    .setMoney(ticket.getMoney())
                    .setCreateTime(perform.getCreateTime()).setPageViews(perform.getPageViews());
            list.add(thumbnail);
        }
        return list;
    }

    @Override
    @Cacheable(value = "default", keyGenerator = "cacheKeyGenerator")
    public List<PerformThumbnail> getRecommendList(Integer categoryId) {
        QueryWrapper<Perform> query = new QueryWrapper<>();
        query.eq("category_id", categoryId).orderByDesc("page_views");
        List<Perform> performs = performMapper.selectList(query);
        List<PerformThumbnail> thumbnails = combinedData(performs);
        List<PerformThumbnail> result = new ArrayList<>();
        for (int i=0; i<7; i++) {
            result.add(thumbnails.get(i));
        }
        return result;
    }

    @Override
    @Cacheable(value = "default", keyGenerator = "cacheKeyGenerator")
    public PageData<List<PerformThumbnail>> searchPerform(Long pageNum, String str,
                                                          Integer orderType, Map<String, Object> conditions) {
        QueryWrapper<Artist> artistQuery = new QueryWrapper<>();
        artistQuery.like("name", str);
        List<Artist> artists = artistMapper.selectList(artistQuery);
        Page<Perform> page = new Page<>(pageNum, 30);
        IPage<Perform> iPage = performMapper.searchPerform(page, str, artists, orderType, conditions);
        List<Perform> performList = iPage.getRecords();
        PageData<List<PerformThumbnail>> pageData = new PageData<>();
        return pageData.setCurPage(iPage.getCurrent())
                .setData(combinedData(performList))
                .setTotalPage(iPage.getPages())
                .setTotal(iPage.getTotal());
    }

    @Override
    @Cacheable(value = "default", keyGenerator = "cacheKeyGenerator")
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

    @Override
    public boolean addViewPages(Long performId) {
        return performMapper.updateViewPages(performId) == 1;
    }
}
