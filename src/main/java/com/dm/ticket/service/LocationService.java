package com.dm.ticket.service;


import com.dm.ticket.model.entity.Location;

import java.util.List;

public interface LocationService {

    List<Location> getAllLocation(Long id);

    boolean isExist(Long cityId, String detail);

    boolean addNewDetail(Long cityId, String detail);

    List<Location> searchLocation(Long cityId, String detail);
}
