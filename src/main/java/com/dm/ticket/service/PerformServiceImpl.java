package com.dm.ticket.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dm.ticket.mapper.*;
import com.dm.ticket.model.PageData;
import com.dm.ticket.model.dto.PerformDetailDto;
import com.dm.ticket.model.dto.PerformDto;
import com.dm.ticket.model.entity.Perform;
import com.dm.ticket.model.entity.Time;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PerformServiceImpl implements PerformService {

    private PerformMapper performMapper;
    private CategoryMapper categoryMapper;
    private SubclassMapper subclassMapper;
    private ArtistMapper artistMapper;
    private CityMapper cityMapper;
    private LocationMapper locationMapper;
    private TimeMapper timeMapper;

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

    @Override
    public boolean addPerform(PerformDto dto) {
        Perform perform = new Perform();
        BeanUtils.copyProperties(dto, perform);
        return performMapper.insert(perform) == 1;
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
    public Perform getPerformById(Long id) {
        return performMapper.selectById(id);
    }

    @Override
    public PageData<List<PerformDetailDto>> getAllPerformByPage(Long pageNum) {
        Page<Perform> page = new Page<>(pageNum, 30);
        IPage<Perform> data = performMapper.getData(page);
        PageData<List<PerformDetailDto>> pageData = new PageData<>();
        List<Perform> performList = data.getRecords();
        pageData.setData(getDetail(performList)).setCurPage(data.getCurrent());
        return pageData;
    }

    private List<PerformDetailDto> getDetail(List<Perform> performs){
        List<PerformDetailDto> list = new ArrayList<>();
        for (Perform perform : performs) {
            PerformDetailDto dto = new PerformDetailDto();
            QueryWrapper<Time> query = new QueryWrapper<>();
            query.eq("perform_id", perform.getId());
            List<Time> times = timeMapper.selectList(query);
            dto.setId(perform.getId()).setCover(perform.getCover())
                    .setTitle(perform.getTitle()).setContent(perform.getContent())
                    .setTicketNotice(perform.getTicketNotice()).setViewNotice(perform.getViewNotice())
                    .setCategory(categoryMapper.selectById(perform.getCategoryId()))
                    .setSubclass(subclassMapper.selectById(perform.getSubclassId()))
                    .setArtist(artistMapper.selectById(perform.getArtistId()))
                    .setCity(cityMapper.selectById(perform.getCityId()))
                    .setLocation(locationMapper.selectById(perform.getLocationId()))
                    .setTimes(times);
            list.add(dto);
        }
        return list;
    }
}
