package com.dm.ticket.service;


import com.dm.ticket.model.dto.ShipDto;
import com.dm.ticket.model.entity.Ship;

import java.util.List;

public interface ShipService {

    boolean saveShip(Ship ship);

    boolean updateShip(ShipDto dto);

    boolean deleteShip(Long id);

    List<ShipDto> getShipList(Long uid);
}
