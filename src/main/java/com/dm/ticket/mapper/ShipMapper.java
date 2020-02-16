package com.dm.ticket.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dm.ticket.model.dto.ShipDto;
import com.dm.ticket.model.entity.Ship;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface ShipMapper extends BaseMapper<Ship> {

    @Select("select id, name, phone, location from ship where uid = #{uid}")
    List<ShipDto> selectListByUid(Long uid);
}
