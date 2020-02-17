package com.dm.ticket.service;


import com.dm.ticket.model.entity.City;

import java.util.List;

public interface CityService {

    List<City> getCityList();

    boolean addNewCity(String cityName);

    boolean isExist(String cityName);

    City getNameById(Long id);
}
