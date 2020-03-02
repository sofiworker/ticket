package com.dm.ticket.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dm.ticket.mapper.CityMapper;
import com.dm.ticket.model.entity.City;
import com.dm.ticket.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class CityServiceImpl implements CityService {

    private CityMapper cityMapper;

    @Autowired
    public void setCityMapper(CityMapper cityMapper) {
        this.cityMapper = cityMapper;
    }

    @Override
    @Cacheable(value = "default", keyGenerator = "cacheKeyGenerator")
    public List<City> getCityList() {
        return cityMapper.selectList(null);
    }

    @Override
    public boolean addNewCity(String cityName) {
        City city = new City();
        city.setName(cityName);
        return cityMapper.insert(city) == 1;
    }

    @Override
    public boolean isExist(String cityName) {
        QueryWrapper<City> query = new QueryWrapper<>();
        query.eq("name", cityName);
        return cityMapper.selectOne(query) == null;
    }

    @Override
    public City getNameById(Long id) {
        return cityMapper.selectById(id);
    }

    @Override
    @Cacheable(value = "default", keyGenerator = "cacheKeyGenerator")
    public List<City> searchCity(String name) {
        QueryWrapper<City> query = new QueryWrapper<>();
        query.like("name", name);
        return cityMapper.selectList(query);
    }
}
