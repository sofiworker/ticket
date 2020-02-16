package com.dm.ticket.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dm.ticket.mapper.ShipMapper;
import com.dm.ticket.model.dto.ShipDto;
import com.dm.ticket.model.entity.Ship;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShipServiceImpl implements ShipService {

    private ShipMapper shipMapper;

    @Autowired
    public void setShipMapper(ShipMapper shipMapper) {
        this.shipMapper = shipMapper;
    }

    @Override
    public boolean saveShip(Ship ship) {
        return shipMapper.insert(ship) == 1;
    }

    @Override
    public boolean updateShip(ShipDto dto) {
        Ship ship = new Ship();
        BeanUtils.copyProperties(dto, ship);
        return shipMapper.updateById(ship) == 1;
    }

    @Override
    public boolean deleteShip(Long id) {
        return shipMapper.deleteById(id) == 1;
    }

    @Override
    public List<ShipDto> getShipList(Long uid) {
        return shipMapper.selectListByUid(uid);
    }
}
