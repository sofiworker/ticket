package com.dm.ticket.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dm.ticket.model.entity.Time;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface TimeMapper extends BaseMapper<Time> {
}
