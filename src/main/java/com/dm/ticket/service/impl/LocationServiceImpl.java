package com.dm.ticket.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dm.ticket.mapper.LocationMapper;
import com.dm.ticket.model.entity.Location;
import com.dm.ticket.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class LocationServiceImpl implements LocationService {

    private LocationMapper mapper;

    @Autowired
    public void setMapper(LocationMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    @Cacheable(value = "default", keyGenerator = "cacheKeyGenerator")
    public List<Location> getAllLocation(Long id) {
        QueryWrapper<Location> query = new QueryWrapper<>();
        query.eq("city_id", id);
        return mapper.selectList(query);
    }

    @Override
    public boolean isExist(Long id, String detail) {
        QueryWrapper<Location> query = new QueryWrapper<>();
        query.eq("city_id", detail).eq("detail", detail);
        return mapper.selectOne(query) != null;
    }

    @Override
    public boolean addNewDetail(Long cityId, String detail) {
        Location location = new Location();
        location.setCityId(cityId);
        location.setDetail(detail);
        return mapper.insert(location) == 1;
    }

    @Override
    @Cacheable(value = "default", keyGenerator = "cacheKeyGenerator")
    public List<Location> searchLocation(Long cityId, String detail) {
        QueryWrapper<Location> query = new QueryWrapper<>();
        query.eq("city_id", cityId).like("detail", detail);
        return mapper.selectList(query);
    }
}
